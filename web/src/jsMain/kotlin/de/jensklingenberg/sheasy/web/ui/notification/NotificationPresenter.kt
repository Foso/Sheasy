package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import de.jensklingenberg.sheasy.web.model.response.NotificationResponse
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.network.ApiEndPoint.Companion.notificationWebSocketURL
import de.jensklingenberg.sheasy.web.usecase.NotificationOptions
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class NotificationPresenter(private val view: NotificationContract.View) : NotificationContract.Presenter,
    MyWebSocket.WebSocketListener {

    var myWebSocket = MyWebSocket(notificationWebSocketURL)

    /****************************************** React Lifecycle methods  */

    var viewIsUnmounted = false

    init {
        myWebSocket.listener = this
    }

    override fun componentDidMount() {
        myWebSocket.open()
    }

    override fun componentWillUnmount() {
        viewIsUnmounted = true
    }

    /****************************************** MyWebSocket methods  */


    override fun onMessage(messageEvent: MessageEvent) {
        if(!viewIsUnmounted) {
            console.log("View is alive")

            val notificationResponse = JSON.parse<NotificationResponse>(messageEvent.data.toString())

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
            console.log(messageEvent.data)
        }else{
            console.log("View is dead")

        }

    }


    override fun onError(messageEvent: Event) {}

}