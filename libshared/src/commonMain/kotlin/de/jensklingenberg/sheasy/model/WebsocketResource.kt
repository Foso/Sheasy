package de.jensklingenberg.sheasy.model

data class WebsocketResource<T>(val type: WebSocketType, val data: T?, val message: String="") {

}

enum class WebSocketType{
    Notification,

    MESSAGE
}