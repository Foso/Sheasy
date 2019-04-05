package de.jensklingenberg.sheasy.web.network

import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

interface Websocket{


    fun send(message:String)

    fun close()

    fun open()
    interface WebSocketListener {
        fun onMessage(messageEvent: MessageEvent)
        fun onError(messageEvent: Event)
    }

    fun addListener(listener: MyWebSocket.WebSocketListener)
}