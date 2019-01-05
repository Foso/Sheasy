package de.jensklingenberg.sheasy.network.websocket

interface NanoWSDWebSocketDataSource {

    val screenShareWebSocketMap: HashMap<String, ScreenShareWebSocket>


    fun addListener(webSocketListener: WebSocketListener)
    fun start()
    fun stop()


}
