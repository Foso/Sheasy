package de.jensklingenberg.sheasy.web.data.repository

import de.jensklingenberg.sheasy.model.*
import de.jensklingenberg.sheasy.web.data.EventDataSource
import de.jensklingenberg.sheasy.web.model.NotificationOptions
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.network.HttpAPI
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.network.Websocket
import de.jensklingenberg.sheasy.web.ui.share.ShareSourceItem
import kodando.rxjs.Observable
import kodando.rxjs.Subject
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

class EventRepository : EventDataSource, MyWebSocket.WebSocketListener {


    private var item: ArrayList<SourceItem> = arrayListOf()
    private var shareWebSocket: Websocket? = null
    private var notificationWebSocket: Websocket? = null

    private val messages: Subject<List<SourceItem>> = Subject()
    private val notifications: Subject<NotificationOptions> = Subject()
    private val clientEvents: Subject<ClientEvent> = Subject()


    override fun sendMessage(message: String) {
        item.add(ShareSourceItem(ShareItem(message), ShareType.OUTGOING))
        shareWebSocket?.send(message)
        messages.next(item)

    }


    override fun onError(messageEvent: Event) {
        messages.error(SheasyError.UNKNOWNERROR())
    }

    override fun onClose(messageEvent: Event) {
        messages.complete()
    }

    override fun onOpen(event: Event) {}

    override fun onMessage(messageEvent: MessageEvent) {

        console.log(
            "hUHU" + messageEvent.data
        )


        console.log("HHHHH" + getWebsocketType(messageEvent.data.toString()).name)

        when (getWebsocketType(messageEvent.data.toString())) {
            WebSocketType.MESSAGE -> {
                val resource = JSON.parse<WebsocketResource<ShareItem>>(messageEvent.data.toString())

                val shareItem = resource.data!!

                item.add(ShareSourceItem(ShareItem(shareItem.message.toString()), ShareType.INCOMING))

                console.log("SHARE" + shareItem)
                messages.next(item)

            }
            WebSocketType.Notification -> {
                val resource = JSON.parse<WebsocketResource<Notification>>(messageEvent.data.toString())


                val notificationResponse = resource.data!!

                val notificationOptions = NotificationOptions(
                    title = notificationResponse.title,
                    subText = notificationResponse.subText,
                    icon = "",
                    tag = notificationResponse.postTime
                )

                console.log(resource.type.toString())
                notifications.next(notificationOptions)


            }
            WebSocketType.EVENT -> {
                val resource: WebsocketResource<ClientEvent> = JSON.parse<WebsocketResource<ClientEvent>>(messageEvent.data.toString())


                clientEvents.next(resource.data!!)
                console.log("EVENT1" + resource.data!!.message)
                // notifications.next(notificationOptions)


            }

            else -> {

            }
        }

        console.log(messageEvent.data)


    }

    private fun getWebsocketType(toString: String): WebSocketType {
        //TODO:Find better way to get type
        return when {
            toString.contains("\"type\":\"${WebSocketType.MESSAGE.name}\"") -> {
                WebSocketType.MESSAGE
            }
            toString.contains("\"type\":\"${WebSocketType.Notification.name}\"") -> {
                WebSocketType.Notification
            }
            toString.contains("\"type\":\"${WebSocketType.EVENT.name}\"") -> {
                WebSocketType.EVENT
            }
            else -> {
                WebSocketType.UNKNOWN
            }

        }

    }

    override fun observeEvents(): Observable<List<SourceItem>> {
        console.log("View is alive")

        shareWebSocket = MyWebSocket(HttpAPI.shareWebSocketURL, this)
        return messages

    }

    override fun observeClientEvents(): Observable<ClientEvent> {
        console.log("View is alive")

        if (notificationWebSocket == null) {
            notificationWebSocket = MyWebSocket(HttpAPI.notificationWebSocketURL, this)
        }

        return clientEvents

    }

    override fun observNotifcations(): Observable<NotificationOptions> {
        if (notificationWebSocket == null) {
            notificationWebSocket = MyWebSocket(HttpAPI.notificationWebSocketURL, this)
        }
        return notifications

    }

}