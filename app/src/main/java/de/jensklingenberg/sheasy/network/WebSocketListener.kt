package de.jensklingenberg.sheasy.network

import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

interface WebSocketListener {
    fun openWebSocket(session: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket
}
