package de.jensklingenberg.sheasy.data.websocket

import de.jensklingenberg.sheasy.network.ScreenShareWebSocket
import de.jensklingenberg.sheasy.network.WebSocketListener

interface NanoWSDWebSocketDataSource {

    val screenShareWebSocketMap: HashMap<String, ScreenShareWebSocket>


    fun addListener(webSocketListener: WebSocketListener)
    fun start()
    fun stop()


}
