package de.jensklingenberg.sheasy.network.websocket.websocket

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


open class ShareWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake) {

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


    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        compositeDisposable.dispose()

    }

    override fun onMessage(message: NanoWSD.WebSocketFrame) {
        Log.d(TAG, message.textPayload.toString())
        Log.d(TAG, "onMessage: " + message)
        message.setUnmasked()
        eventDataSource.addEvent(Event(EventCategory.SHARE, message.textPayload))

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


                    send(moshi.toJson(it))


        }).addTo(compositeDisposable)

    }

    override fun send(payload: String?) {
        if (isOpen) {
            Observable
                .fromCallable {

                    val typeA = Types.newParameterizedType(Resource::class.java, ShareItem::class.java)
                    val adapter = moshi.adapter<Resource<ShareItem>>(typeA)

                    super.send(
                        adapter?.toJson(
                            Resource.success(
                                ShareItem(
                                    payload
                                )
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


        eventDataSource.addEvent(Event(EventCategory.SHARE, payload ?: ""))

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


}
