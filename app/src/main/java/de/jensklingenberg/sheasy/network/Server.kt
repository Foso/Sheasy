package de.jensklingenberg.sheasy.network

import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketDataSource
import de.jensklingenberg.sheasy.network.websocket.WebSocketListener
import de.jensklingenberg.sheasy.utils.UseCase.VibrationUseCase
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.ktor.server.netty.NettyApplicationEngine
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Server : WebSocketListener {


    enum class DataDestination {
        SCREENSHARE, SHARE
    }

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource


    @Inject
    lateinit var nanoWSDWebSocketDataSource: NanoWSDWebSocketDataSource

    @Inject
    lateinit var vibrationUseCase: VibrationUseCase

    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var nettyApplicationEngine: NettyApplicationEngine


    init {
        initializeDagger()

        nanoWSDWebSocketDataSource.addListener(this)
    }


    /****************************************** Lifecycle methods  */


    private fun initializeDagger() = App.appComponent.inject(this)

    fun start() {
        Log.d("Server", "Server running")



        nanoWSDWebSocketDataSource.start()


        Single.fromCallable {
            nettyApplicationEngine.start(wait = true)
            true
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onError = {
                Log.d("runIn",it.message)
                nettyApplicationEngine.stop(0L, 0L, TimeUnit.SECONDS)
            }, onSuccess = {}).addTo(compositeDisposable)


        vibrationUseCase.vibrate()

    }

    fun stop() {
        Log.d("Server", "Server stopped")
        nettyApplicationEngine.stop(0L, 0L, TimeUnit.SECONDS)
        compositeDisposable.dispose()

        nanoWSDWebSocketDataSource.stop()

        vibrationUseCase.vibrate()

    }


    /****************************************** Class methods  */


    fun sendData(dataDestination: DataDestination, data: String) {
        when (dataDestination) {

            DataDestination.SCREENSHARE -> {
                nanoWSDWebSocketDataSource.screenShareWebSocketMap.values.forEach {
                    it.send(data)
                }
            }
            DataDestination.SHARE -> {
                nanoWSDWebSocketDataSource.shareWebSocket?.send(data)
            }
        }
    }

    fun sendData(dataDestination: DataDestination, data: ByteArray) {
        when (dataDestination) {

            DataDestination.SCREENSHARE -> {
                nanoWSDWebSocketDataSource.screenShareWebSocketMap.values.forEach {
                    it.send(data)

                }
            }
            DataDestination.SHARE -> {
                nanoWSDWebSocketDataSource.shareWebSocket?.send(data)
            }
        }
    }


    override fun openWebSocket(session: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {
        return nanoWSDWebSocketDataSource.openWebSocket(session)

    }


}

