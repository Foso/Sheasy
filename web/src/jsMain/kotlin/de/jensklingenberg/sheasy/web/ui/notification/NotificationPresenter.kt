package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.model.WebSocketType
import de.jensklingenberg.sheasy.model.WebsocketResource
import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import de.jensklingenberg.sheasy.web.model.NotificationOptions
import de.jensklingenberg.sheasy.web.model.response.NotificationResponse
import de.jensklingenberg.sheasy.web.network.ApiEndPoint.Companion.notificationWebSocketURL
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
            console.log("View is alive")


            val resource = JSON.parse<WebsocketResource<NotificationResponse>>(messageEvent.data.toString())


            when (resource.type.toString()) {
                WebSocketType.Notification.toString() -> {

                    val notificationResponse = resource.data!!


                    // val notificationResponse = JSON.parse<NotificationResponse>(messageEvent.data.toString())

                    val notiOptions = object : ReactNotificationOptions {
                        override var tag: String? = "dd"
                        override var icon: String? = "https://avatars3.githubusercontent.com/u/5015532?s=40&v=4"
                        override var body: String? = notificationResponse.subText
                        override var title: String? = notificationResponse.title
                    }

                    val notificationOptions = NotificationOptions(
                        title = notificationResponse.title,
                        subText = notificationResponse.subText,
                        icon = "https://avatars3.githubusercontent.com/u/5015532?s=40&v=4",
                        tag = notificationResponse.subText
                    )

                    view.showNotification(notificationOptions)
                    console.log(resource.type.toString())
                    console.log(WebSocketType.Notification.toString())

                    if(resource.type.toString().equals(WebSocketType.Notification.toString()) ){

                    }


                    //   console.log(messageEvent.data)


                }
            }

        } else {
            console.log("View is dead")

        }

    }


    override fun onError(messageEvent: Event) {}

}