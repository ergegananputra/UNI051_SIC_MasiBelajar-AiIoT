package com.sic6.masibelajar.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sic6.masibelajar.data.local.PrefManager
import com.sic6.masibelajar.data.remote.WebSocketManager
import com.sic6.masibelajar.domain.dao.HistoryDao
import com.sic6.masibelajar.domain.entities.History
import com.sic6.masibelajar.domain.entities.VideoStreamRequest
import com.sic6.masibelajar.domain.entities.VideoStreamResponse
import com.sic6.masibelajar.domain.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class VideoStreamViewModel @Inject constructor(
    private val historyDao: HistoryDao,
    private val prefManager: PrefManager
) : ViewModel() {

    private val webSocketManager = WebSocketManager("ws://10.33.201.99:8000/v1/main-con")

    private val _response = MutableStateFlow<VideoStreamResponse?>(null)
    val response = _response.asStateFlow()

    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history = _history.asStateFlow()

    init {
        viewModelScope.launch {
            historyDao.deleteOldHistory()
            _history.value = historyDao.getAllHistory()
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

            val currentTimestamp = message.data.results.timestamp
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val currentDateTime = LocalDateTime.parse(currentTimestamp, formatter)

            // new event every 5 minute
            val shouldAddNewEvent = history.value.isEmpty() || history.value.lastOrNull()?.let { lastHistory ->
                val lastDateTime = LocalDateTime.parse(lastHistory.time, formatter)
                ChronoUnit.MINUTES.between(lastDateTime, currentDateTime) >= 4
            } ?: true // If history is empty, always add the first event

            if (shouldAddNewEvent) {
                if (message.data.results.fall) {
                    val hist = History(
                        type = EventType.FALL,
                        name = "Fall Accident",
                        time = message.data.results.timestamp
                    )
                    _history.value += hist
                    historyDao.insertHistory(hist)
                } else if (message.data.results.is_there_something_wrong == true) {
                    val hist = History(
                        type = EventType.MISSING,
                        name = "Someone has been inside the safe zone for an extended period",
                        time = message.data.results.timestamp
                    )
                    _history.value += hist
                    historyDao.insertHistory(hist)
                }
            }
        }
    }

    fun send(message: VideoStreamRequest) {
        webSocketManager.disconnect()
        webSocketManager.connect()
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
