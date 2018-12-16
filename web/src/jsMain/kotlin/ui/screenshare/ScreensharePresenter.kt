package ui.screenshare

import network.MyWebSocket
import network.NetworkUtil
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class ScreensharePresenter(val view: ScreenshareContract.View) : ScreenshareContract.Presenter,
    MyWebSocket.WebSocketListener {

    var myWebSocket = MyWebSocket(NetworkUtil.screenshareWebSocketURL)

    /****************************************** React Lifecycle methods  */
    init {
        myWebSocket.listener = this
    }

    override fun componentDidMount() {
        myWebSocket.open()
    }

    override fun componentWillUnmount() {}


    override fun onError(messageEvent: Event) {}

    override fun onMessage(messageEvent: MessageEvent) {
        val notificationResponse = messageEvent.data.toString()

        view.setData(notificationResponse)
        console.log(messageEvent.data)
    }


}