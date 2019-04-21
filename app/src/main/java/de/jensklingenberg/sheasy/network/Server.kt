package de.jensklingenberg.sheasy.network

import android.app.NotificationManager
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketDataSource
import de.jensklingenberg.sheasy.data.usecase.VibrationUseCase
import io.ktor.server.netty.NettyApplicationEngine
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Server {

    companion object{
        val serverRunning: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()

    }

    enum class DataDestination {
        SCREENSHARE, SHARE
    }

    val compositeDisposable = CompositeDisposable()


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource


    @Inject
    lateinit var nanoWSDWebSocketDataSource: NanoWSDWebSocketDataSource

    @Inject
    lateinit var vibrationUseCase: VibrationUseCase


    @Inject
    lateinit var nettyApplicationEngine: NettyApplicationEngine


    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    init {
        initializeDagger()
    }


    /****************************************** Lifecycle methods  */


    private fun initializeDagger() = App.appComponent.inject(this)

    fun start() {

        Log.d("Server", "Server running")

        nanoWSDWebSocketDataSource.start()
        serverRunning.onNext(true)

        Single.fromCallable {
            nettyApplicationEngine.start(wait = true)

            true
        }
            .subscribeOn(Schedulers.io())
            .subscribeBy(onError = {
                Log.d("Server", it.message)
                nettyApplicationEngine.stop(0L, 0L, TimeUnit.SECONDS)
            }).addTo(compositeDisposable)
        notificationUseCase.showServerNotification()

        vibrationUseCase.vibrate()

    }

    fun stop() {
        serverRunning.onNext(false)

        notificationUseCase.cancelAll()
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


}

