package network

import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

import org.w3c.dom.events.Event

class MyWebSocket(url: String) {

    var listener: WebSocketListener? = null
    val webSocket = WebSocket(url)

    init {
        webSocket.onmessage = { tt: Event ->
            listener?.onMessage((tt as MessageEvent))
        }
    }


    fun open() {
        webSocket.onopen = { tt: Event ->
            console.log(tt)
            webSocket.send("Hugner")
        }
    }

    interface WebSocketListener {
        fun onMessage(messageEvent: MessageEvent)
    }


}