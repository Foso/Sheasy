package ui.notification

import components.Notification.ReactNotificationOptions
import model.NotificationResponse
import network.MyWebSocket
import network.NetworkUtil.Companion.notificationWebSocketURL
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class NotificationPresenter(private val view: NotificationContract.View) : NotificationContract.Presenter,
    MyWebSocket.WebSocketListener {

    var myWebSocket = MyWebSocket(notificationWebSocketURL)

    /****************************************** React Lifecycle methods  */

    init {
        myWebSocket.listener = this
    }

    override fun componentDidMount() {
        myWebSocket.open()
    }

    override fun componentWillUnmount() {}

    /****************************************** MyWebSocket methods  */


    override fun onMessage(messageEvent: MessageEvent) {

        val notificationResponse = JSON.parse<NotificationResponse>(messageEvent.data.toString())

        val notiOptions = object : ReactNotificationOptions {
            override var tag: String? = "dd"
            override var icon: String? = "https://avatars3.githubusercontent.com/u/1381907?s=40&v=4"
            override var body: String? = notificationResponse.subText
            override var title: String? = notificationResponse.title
        }

        view.showNotification(notiOptions)
        console.log(messageEvent.data)
    }


    override fun onError(messageEvent: Event) {}

}