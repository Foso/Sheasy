package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.model.Notification
import de.jensklingenberg.sheasy.model.WebSocketType
import de.jensklingenberg.sheasy.model.WebsocketResource
import de.jensklingenberg.sheasy.web.model.NotificationOptions
import de.jensklingenberg.sheasy.web.network.HttpAPI.Companion.notificationWebSocketURL
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.network.Websocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class NotificationPresenter(private val view: NotificationContract.View) : NotificationContract.Presenter {
    override fun onOpen(event: Event) {


    }

    override fun onClose(messageEvent: Event) {


    }

    var myWebSocket: Websocket? = null

    /****************************************** React Lifecycle methods  */

    var viewIsUnmounted = false


    override fun componentDidMount() {
        myWebSocket = MyWebSocket(notificationWebSocketURL, this)
    }

    override fun componentWillUnmount() {
        myWebSocket?.close()
        viewIsUnmounted = true
    }

    /****************************************** MyWebSocket methods  */


    override fun onMessage(messageEvent: MessageEvent) {
        if (!viewIsUnmounted) {


            val resource = JSON.parse<WebsocketResource<Notification>>(messageEvent.data.toString())


            when (resource.type.toString()) {
                WebSocketType.Notification.toString() -> {

                    val notificationResponse = resource.data!!

                    val notificationOptions = NotificationOptions(
                        title = notificationResponse.title,
                        subText = notificationResponse.subText,
                        icon = "",
                        tag = notificationResponse.postTime
                    )

                    view.showNotification(notificationOptions)
                    console.log(resource.type.toString())
                    console.log(WebSocketType.Notification.toString())

                }
            }

        } else {
            console.log("View is dead")

        }

    }


    override fun onError(messageEvent: Event) {}

}