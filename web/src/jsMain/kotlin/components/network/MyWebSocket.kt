package components.network

import de.jensklingenberg.react.network.Websocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

class MyWebSocket(url: String) : Websocket {

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


    override fun open() {
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