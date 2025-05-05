package com.sic6.masibelajar.data.remote

import android.util.Log
import com.sic6.masibelajar.domain.entities.VideoStreamRequest
import com.sic6.masibelajar.domain.entities.VideoStreamResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketManager(
    private val url: String
) : WebSocketListener() {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _messages = MutableSharedFlow<VideoStreamResponse>(replay = 1)
    val messages = _messages.asSharedFlow() // UI observes this

    private val jsonParser = Json { ignoreUnknownKeys = true }
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun connect() {
        val request = Request.Builder()
            .url(url)
            .build()
        webSocket = client.newWebSocket(request, this)
        Log.d("websocket", "connected to $url")
    }

    fun send(message: VideoStreamRequest) {
        Log.d("websocket", "send: $message")
        webSocket?.send(Json.encodeToString(message))
    }

    fun disconnect() {
        webSocket?.close(1000, "Goodbye")
        Log.d("websocket", "disconnected")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        coroutineScope.launch {
            Log.d("websocket", "response: $text")
            try {
                val parsed = jsonParser.decodeFromString<VideoStreamResponse>(text)
                _messages.emit(parsed)
            } catch (e: Exception) {
                Log.e("websocket", e.message ?: "Unknown error")
                disconnect()
            }
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("websocket", t.message ?: "Unknown error")
        disconnect()
    }
}