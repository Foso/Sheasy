package de.jensklingenberg.sheasy.web.ui.share

import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.web.model.StringSourceItem
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.network.Websocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class SharePresenter(val view: ShareContract.View) : ShareContract.Presenter,
    MyWebSocket.WebSocketListener {

    var myWebSocket: Websocket = MyWebSocket(ApiEndPoint.shareWebSocketURL)

    /****************************************** React Lifecycle methods  */

    var viewIsUnmounted = false

    init {
        myWebSocket.addListener(this)
    }

    override fun componentDidMount() {
        myWebSocket.open()
    }

    override fun componentWillUnmount() {
        myWebSocket.close()
        viewIsUnmounted = true
    }

    override fun send(message: String) {
        console.log("HI" + message)
        myWebSocket.send(message)
    }

    /****************************************** MyWebSocket methods  */


    override fun onMessage(messageEvent: MessageEvent) {
        if (!viewIsUnmounted) {
            console.log("View is alive")

            val notificationResponse = JSON.parse<Resource<ShareItem>>(messageEvent.data.toString()).data!!


            view.showMessage(
                StringSourceItem(notificationResponse.subText.toString())
            )
            console.log(messageEvent.data)
        } else {
            console.log("View is dead")

        }

    }


    override fun onError(messageEvent: Event) {}

}