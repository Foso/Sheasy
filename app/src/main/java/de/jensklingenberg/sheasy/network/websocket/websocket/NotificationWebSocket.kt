package de.jensklingenberg.sheasy.network.websocket.websocket

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.model.Notification
import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.model.WebSocketType
import de.jensklingenberg.sheasy.model.WebsocketResource
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.WebSocket
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class NotificationWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake), SheasyWebSocket {

    @Inject
    lateinit var moshi: Moshi


    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    val compositeDisposable = CompositeDisposable()

    var isClosed = false
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
                    runInBackground {
                        send(notification)
                    }
                }

            }).addTo(compositeDisposable)

    }

    private fun startRunner() {
        var t = 0
        Observable
            .fromCallable {
                t++
                val pingframe =
                    NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")
                ping(pingframe.binaryPayload)

                true
            }
            .delay(1, TimeUnit.SECONDS)
            .repeatUntil { isClosed }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { _ ->
                //Use result for something
            }.addTo(compositeDisposable)
    }

    override fun send(payload: ByteArray?) {
        super.send(payload)
    }



    override fun onMessage(message: NanoWSD.WebSocketFrame) {
        message.setUnmasked()
    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {
        Log.d(TAG, "onPong: ")
    }

    override fun onException(exception: IOException) {
        Log.d(TAG, "onException: " + exception.message)
    }

    fun send(notification: Notification) {
        val parameterizedType = Types.newParameterizedType(WebsocketResource::class.java, Notification::class.java)
        val adapter = moshi.adapter<WebsocketResource<Notification>>(parameterizedType)
        send(
            adapter.toJson(
                WebsocketResource(
                    WebSocketType.Notification, notification, ""
                )

            )
        )

    }

    override fun send(shareItem: ShareItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        Log.d(TAG, "onClose")
        isClosed = true
        compositeDisposable.dispose()

    }

}
