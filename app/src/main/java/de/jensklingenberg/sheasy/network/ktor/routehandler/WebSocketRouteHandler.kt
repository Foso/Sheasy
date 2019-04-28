package de.jensklingenberg.sheasy.network.ktor.routehandler

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.model.*
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.Route
import io.ktor.websocket.webSocket
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class WebSocketRouteHandler{



    var channel: SendChannel<Frame>? = null
    var notification: SendChannel<Frame>? = null

    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var eventDataSource: EventDataSource

    init {
        initializeDagger()

        notificationDataSource
            .notificationSubject
            .subscribeBy(onNext = { notificatio ->

                val parameterizedType =
                    Types.newParameterizedType(WebsocketResource::class.java, Notification::class.java)
                val adapter = moshi.adapter<WebsocketResource<Notification>>(parameterizedType)

                notification?.let {
                    GlobalScope.launch {
                        it.send(
                            Frame.Text(
                                adapter.toJson(
                                    WebsocketResource(
                                        WebSocketType.Notification, notificatio, ""
                                    )

                                )
                            )
                        )
                    }
                }
            }).addTo(compositeDisposable)

    }

    private fun initializeDagger() = App.appComponent.inject(this)

    fun send(shareItem: ShareItem) {
        val typeA = Types.newParameterizedType(WebsocketResource::class.java, ShareItem::class.java)
        val adapter = moshi.adapter<WebsocketResource<ShareItem>>(typeA)

        channel?.let {
            GlobalScope.launch {
                it.send(
                    Frame.Text(
                        adapter?.toJson(
                            WebsocketResource(
                                WebSocketType.MESSAGE,shareItem, ""
                            )
                        ) ?: ""
                    )
                )
            }

            eventDataSource.addEvent(MessageEvent(shareItem.message ?: "", Date().toString(), MessageType.OUTGOING))
        }
    }


    fun websocket(route:Route){
        with(route){

            webSocket("notification") {
                // websocketSession
                notification = outgoing
                incoming.consumeEach { frame ->

                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            outgoing.send(Frame.Text("YOU SAID: $text"))
                            if (text.equals("bye", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                            }
                        }
                        is Frame.Ping -> {

                            outgoing.send(Frame.Text("YOU SAID: "))

                        }

                        is Frame.Pong -> {

                            outgoing.send(Frame.Text("YOU SAID: "))

                        }

                        is Frame.Close -> {

                            outgoing.send(Frame.Text("YOU SAID: "))

                        }

                        is Frame.Binary -> {

                            outgoing.send(Frame.Text("YOU SAID: "))

                        }


                    }
                }


            }

            webSocket("share") {
                // websocketSession

                channel = outgoing

                incoming.consumeEach { frame ->

                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()



                            eventDataSource.addEvent(MessageEvent(text ?: "", Date().toString(), MessageType.INCOMING))


                            if (text.equals("bye", ignoreCase = true)) {
                                close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                            }

                        }
                    }


                }
            }


        }


    }
}