package de.jensklingenberg.sheasy.network.websocket

import de.jensklingenberg.sheasy.network.websocket.websocket.SheasyWebSocket
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

interface NanoWSDWebSocketDataSource {

    var shareWebSocket: SheasyWebSocket?

    fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket
    fun start()
    fun stop()


}
