package de.jensklingenberg.sheasy.network.websocket

import android.annotation.SuppressLint
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.EventCategory
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.utils.extension.toJson
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class ShareWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake) {

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var eventDataSource: EventDataSource

    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    val compositeDisposable = CompositeDisposable()

    var isClosed = false
    val TAG = javaClass.simpleName

    init {
        initializeDagger()
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
        Log.d(TAG, message.textPayload.toString())
        Log.d(TAG, "onMessage: " + message)
        message.setUnmasked()
        eventDataSource.addEvent(Event(EventCategory.CONNECTION, message.textPayload))

    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {
        Log.d(TAG, "onPong: ")
    }

    override fun onException(exception: IOException) {
        Log.d(TAG, "onException: " + exception.message)
    }


    override fun onOpen() {
        startRunner()
        compositeDisposable.add(
            notificationDataSource.notification.subscribeBy(onNext = {
                Log.d(TAG, "onComp: ")

                if (isOpen) {
                    runInBackground {
                        send(moshi.toJson(it))
                    }
                }

            })
        )
    }

    override fun send(payload: String?) {

        Observable
            .fromCallable {

                val typeA = Types.newParameterizedType(Resource::class.java, ShareItem::class.java)
                val adapter = moshi.adapter<Resource<ShareItem>>(typeA)

                super.send(
                    adapter?.toJson(
                        Resource.success(
                            ShareItem(
                                "test.package",
                                "Testnotification",
                                "testtext",
                                payload,
                                0L
                            )
                        )
                    ) ?: ""
                )


                true
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe {
            }

        eventDataSource.addEvent(Event(EventCategory.CONNECTION, payload ?: ""))

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



                true
            }
            .delay(1, TimeUnit.SECONDS)
            .repeatUntil { isClosed }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { _ ->
                //Use result for something
            }


    }


}
