package com.sic6.masibelajar.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sic6.masibelajar.data.local.PrefManager
import com.sic6.masibelajar.data.remote.WebSocketManager
import com.sic6.masibelajar.domain.entities.History
import com.sic6.masibelajar.domain.entities.VideoStreamRequest
import com.sic6.masibelajar.domain.entities.VideoStreamResponse
import com.sic6.masibelajar.domain.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoStreamViewModel @Inject constructor(
    private val prefManager: PrefManager
) : ViewModel() {

    private val webSocketManager = WebSocketManager("ws://192.168.165.101:8000/v1/main-con")

    private val _response = MutableStateFlow<VideoStreamResponse?>(null)
    val response = _response.asStateFlow()

    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history = _history.asStateFlow()

    init {
        viewModelScope.launch {
            listenWebSocket()
        }
    }

    private fun startStream() {
        send(
            VideoStreamRequest(
                id = "stream",
                points = prefManager.getPoints().map { point -> listOf(point.x, point.y) },
                url = prefManager.getUrl(),
//                url = "ws://10.33.35.199:8000/v1/main-con",
//                url = "storages/sample/Stream2.mp4",
//                url = "test/data/Fall.mp4",
                target_class = prefManager.getTargetClass(),
                time_threshold = prefManager.getTimeThreshold(),
                preview = true,
                track = true
            )
        )
    }

    private suspend fun listenWebSocket() {
        webSocketManager.disconnect()
        webSocketManager.connect()
        startStream()
        webSocketManager.messages.collect { message ->
            _response.value = message

            if ( // new event every 10 minute
                history.value.isEmpty()
                || history.value.last().time.substring(0, 15)
                != message.data.results.timestamp.substring(0, 15)
            ) {
                if (message.data.results.fall) {
                    _history.value += History(
                        type = EventType.FALL,
                        name = "Fall Accident",
                        time = message.data.results.timestamp
                    )
                } else if (message.data.results.is_there_something_wrong == true) {
                    _history.value += History(
                        type = EventType.MISSING,
                        name = "Someone has been inside the safe zone for an extended period",
                        time = message.data.results.timestamp
                    )
                }
            }
        }
    }

    fun send(message: VideoStreamRequest) {
        webSocketManager.send(message)
    }

    private fun parsePoints(points: String): List<List<Int>> {
        var pointList = points.split(";").map { segment ->
            if (segment.isNotBlank()) {
                val values = segment.split(",").map { it.toInt() }
                listOf(values[0], values[1])
            } else {
                listOf(0, 0)
            }
        }
        if (pointList.size < 3) {
            pointList = pointList.toMutableList()
            for (i in pointList.size until 3) {
                pointList.add(listOf(0, 0))
            }
        }
        return pointList
    }

    override fun onCleared() {
        super.onCleared()
        webSocketManager.disconnect()
    }
}
