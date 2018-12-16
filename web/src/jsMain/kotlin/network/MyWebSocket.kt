package network

import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

class MyWebSocket(url: String) {

    var listener: WebSocketListener? = null
    val webSocket = WebSocket(url)

    init {
        webSocket.onmessage = { event: Event ->
            listener?.onMessage((event as MessageEvent))
        }

        webSocket.onerror = { event: Event ->
            listener?.onError((event))
        }

        webSocket.addEventListener("dd", EventListener { })
    }


    fun open() {
        webSocket.onopen = { tt: Event ->
            console.log(tt)
            webSocket.send("Hugner")
        }
    }

    interface WebSocketListener {
        fun onMessage(messageEvent: MessageEvent)
        fun onError(messageEvent: Event)
    }


}