package de.jensklingenberg.sheasy.interfaces

import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

interface MyHttpServer {
    fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket

    fun start(timeout: Int)
    fun stop()
    fun getHostname(): String
}