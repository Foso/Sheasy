package de.jensklingenberg.sheasy.network.websocket.websocket

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.model.*
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class ShareWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake), SheasyWebSocket {


    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var eventDataSource: EventDataSource

    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    val compositeDisposable = CompositeDisposable()

    val TAG = javaClass.simpleName

    init {
        initializeDagger()
    }


    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onOpen() {
        startRunner()

        notificationDataSource
            .notificationSubject
            .subscribeBy(onNext = { notification ->
                Log.d(TAG, "onComp: " + notification.title)

                if (isOpen) {


                    val typeA = Types.newParameterizedType(WebsocketResource::class.java, Notification::class.java)
                    val adapter = moshi.adapter<WebsocketResource<Notification>>(typeA)

                    runInBackground {
                        send(
                            adapter.toJson(
                                WebsocketResource(
                                    WebSocketType.Notification, notification, ""
                                )

                            )
                        )
                    }
                }

            }).addTo(compositeDisposable)
    }

    override fun isOpen(): Boolean {
        return super.isOpen()
    }


    override fun onMessage(message: NanoWSD.WebSocketFrame) {
        message.setUnmasked()
        eventDataSource.addEvent(MessageEvent(message.textPayload ?: "", Date().toString(), MessageType.INCOMING))

    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {
        Log.d(TAG, "onPong: " + pong.textPayload)
    }

    override fun onException(exception: IOException) {
        Log.d(TAG, "onException: " + exception.message)
    }

    override fun send(shareItem: ShareItem) {
        if (isOpen) {
            Observable
                .fromCallable {

                    val typeA = Types.newParameterizedType(WebsocketResource::class.java, ShareItem::class.java)
                    val adapter = moshi.adapter<WebsocketResource<ShareItem>>(typeA)

                    super.send(
                        adapter?.toJson(
                            WebsocketResource(
                                WebSocketType.MESSAGE, ShareItem(
                                    shareItem.message
                                ), ""
                            )
                        ) ?: ""
                    )


                    true
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe {
                }.addTo(compositeDisposable)
        }


        eventDataSource.addEvent(MessageEvent(shareItem.message ?: "", Date().toString(), MessageType.OUTGOING))
    }


    private fun startRunner() {
        var t = 0
        Observable
            .fromCallable {
                t++

                if (isOpen) {
                    val pingframe =
                        NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")
                    ping(pingframe.binaryPayload)
                }

                true
            }
            .delay(1, TimeUnit.SECONDS)
            .repeatUntil { !isOpen }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { _ ->
                //Use result for something
            }.addTo(compositeDisposable)


    }

    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        compositeDisposable.dispose()

    }

}
