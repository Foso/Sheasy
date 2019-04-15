package de.jensklingenberg.sheasy.network.websocket

import de.jensklingenberg.sheasy.network.websocket.websocket.ScreenShareWebSocket
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

interface NanoWSDWebSocketDataSource {

    val screenShareWebSocketMap: HashMap<String, ScreenShareWebSocket>

    fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket
    fun start()
    fun stop()


    var shareWebSocket: NanoWSD.WebSocket?
}
