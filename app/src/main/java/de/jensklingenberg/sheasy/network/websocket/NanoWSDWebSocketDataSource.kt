package de.jensklingenberg.sheasy.network.websocket

import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

interface NanoWSDWebSocketDataSource {

    val screenShareWebSocketMap: HashMap<String, ScreenShareWebSocket>

    fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket
    fun addListener(webSocketListener: WebSocketListener)
    fun start()
    fun stop()


    var shareWebSocket: NanoWSD.WebSocket?
}
