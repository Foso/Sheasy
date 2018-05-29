package de.jensklingenberg.sheasy.interfaces

import de.jensklingenberg.sheasy.network.websocket.NanoWsdWebSocketListener
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

interface MyHttpServer {
    fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket
    fun addWebSocketListenr(nanoWsdWebSocketListener: NanoWsdWebSocketListener)
    fun start(timeout: Int)
    fun stop()
    fun getHostname(): String
}