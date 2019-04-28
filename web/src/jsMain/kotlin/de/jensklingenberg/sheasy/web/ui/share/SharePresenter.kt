package de.jensklingenberg.sheasy.web.ui.share

import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.model.ShareType
import de.jensklingenberg.sheasy.model.WebSocketType
import de.jensklingenberg.sheasy.model.WebsocketResource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.network.HttpAPI.Companion.shareWebSocketURL
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.network.Websocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class SharePresenter(val view: ShareContract.View) : ShareContract.Presenter {
    var myWebSocket: Websocket? = null

    var item: ArrayList<SourceItem> = arrayListOf()

    override fun onOpen(event: Event) {
        console.log((event))
        view.setConnectedMessage("Connected to Server: "+NetworkPreferences().hostname)

    }

    override fun onClose(messageEvent: Event) {
        console.log("m" + messageEvent)
        view.setConnectedMessage("No connection")

    }


    /****************************************** React Lifecycle methods  */

    var viewIsUnmounted = false


    override fun componentDidMount() {
        myWebSocket = MyWebSocket(shareWebSocketURL, this)
        view.setData(item)
    }

    override fun componentWillUnmount() {
        myWebSocket?.close()
        viewIsUnmounted = true
    }

    override fun send(message: String) {
        item.add(ShareSourceItem(ShareItem(message), ShareType.OUTGOING))
        myWebSocket?.send(message)
    }

    /****************************************** MyWebSocket methods  */


    override fun onMessage(messageEvent: MessageEvent) {
        if (!viewIsUnmounted) {
            console.log("View is alive")


            val resource = JSON.parse<WebsocketResource<ShareItem>>(messageEvent.data.toString())


            when (resource.type.toString()) {
                WebSocketType.MESSAGE.toString() -> {

                    val shareItem= resource.data!!

                    item.add(ShareSourceItem(ShareItem(shareItem.message.toString()), ShareType.INCOMING))


                    view.setData(item)

                }
            }



            console.log(messageEvent.data)
        } else {
            console.log("View is dead")

        }
    }


    override fun onError(messageEvent: Event) {}

}