package ui.notification

import components.Notification.ReactNotificationOptions
import network.MyWebSocket
import org.w3c.dom.MessageEvent

class NotificationPresenter(val view: NotificationContract.View) : NotificationContract.Presenter, MyWebSocket.WebSocketListener {

    override fun onMessage(messageEvent: MessageEvent) {

        val notiOptions = object : ReactNotificationOptions {
            override var tag: String? = "dd"
            override var icon: String? = "https://avatars3.githubusercontent.com/u/1381907?s=40&v=4"
            override var body: String? = messageEvent.data.toString()
            override var title: String? = messageEvent.data.toString()

        }

        view.showNotification(notiOptions)

        console.log(messageEvent.data)
    }

    var abc = MyWebSocket("wss://echo.websocket.org")


    init {
        abc.listener = this


    }

    override fun componentWillUnmount() {


    }


    override fun componentDidMount() {
        abc.open()
    }


}