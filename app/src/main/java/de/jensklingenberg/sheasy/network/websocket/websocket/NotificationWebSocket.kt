package de.jensklingenberg.sheasy.network.websocket.websocket

import android.annotation.SuppressLint
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.model.Notification
import de.jensklingenberg.sheasy.model.WebSocketType
import de.jensklingenberg.sheasy.model.WebsocketResource
import de.jensklingenberg.sheasy.utils.extension.toJson
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class NotificationWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake) {

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

    override fun send(payload: ByteArray?) {
        super.send(payload)
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        isClosed = true
        compositeDisposable.dispose()

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


    override fun onOpen() {
        startRunner()

        notificationDataSource.notification.subscribeBy(onNext = {
            Log.d(TAG, "onComp: ")

            if (isOpen) {
                runInBackground {
                    send(moshi.toJson(it))
                }
            }

        }).addTo(compositeDisposable)

    }

    @SuppressLint("CheckResult")
    private fun startRunner() {
        var t = 0
        Observable
            .fromCallable {
                t++
                val pingframe =
                    NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")
                ping(pingframe.binaryPayload)

                val typeA = Types.newParameterizedType(WebsocketResource::class.java, Notification::class.java)
                val adapter = moshi.adapter<WebsocketResource<Notification>>(typeA)

                send(
                    adapter.toJson(

                        WebsocketResource(
                            WebSocketType.Notification, Notification(
                                "test.package",
                                "Testnotification",
                                "testtext",
                                "testsubtext " + t,
                                0L
                            ), ""
                        )

                    )
                )

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


}
