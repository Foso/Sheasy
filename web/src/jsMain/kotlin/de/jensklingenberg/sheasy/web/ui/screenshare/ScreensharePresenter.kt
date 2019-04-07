package de.jensklingenberg.sheasy.web.ui.screenshare

import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.network.Websocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class ScreensharePresenter(val view: ScreenshareContract.View) : ScreenshareContract.Presenter {
    override fun onOpen(event: Event) {


    }

    override fun onClose(messageEvent: Event) {


    }

    var myWebSocket : Websocket?=null

    /****************************************** React Lifecycle methods  */


    override fun componentDidMount() {
        myWebSocket=  MyWebSocket(ApiEndPoint.screenshareWebSocketURL,this)
    }

    override fun componentWillUnmount() {}


    override fun onError(messageEvent: Event) {}

    override fun onMessage(messageEvent: MessageEvent) {
        val notificationResponse = messageEvent.data.toString()

        view.setData(notificationResponse)
        console.log(messageEvent.data)
    }


}