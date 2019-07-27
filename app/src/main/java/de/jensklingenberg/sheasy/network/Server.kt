package de.jensklingenberg.sheasy.network

import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.data.usecase.VibrationUseCase
import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.network.ktor.ktorApplicationModule
import de.jensklingenberg.sheasy.network.ktor.routehandler.WebSocketRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import io.ktor.application.ApplicationStarted
import io.ktor.application.ApplicationStopPreparing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Server {

    companion object {
        val serverRunning: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()

    }

    enum class DataDestination {
        SCREENSHARE, SHARE
    }

    val compositeDisposable = CompositeDisposable()


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var vibrationUseCase: VibrationUseCase

    var applicationEngine: ApplicationEngine? = null


    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var webSocketRouteHandler: WebSocketRouteHandler

    @Inject
    lateinit var generalRouteHandler: GeneralRouteHandler

    @Inject
    lateinit var fileRouteHandler: FileRouteHandler


    init {
        initializeDagger()
        applicationEngine?.environment?.monitor?.subscribe(ApplicationStarted) {
            serverRunning.onNext(true)

        }
    }


    /****************************************** Lifecycle methods  */


    private fun initializeDagger() = App.appComponent.inject(this)

    fun start(): Completable = Completable.create { emitter ->

        try {
            applicationEngine = embeddedServer(
                Netty,
                port = sheasyPrefDataSource.httpPort.toInt(),
                module = {
                    ktorApplicationModule(
                        generalRouteHandler,
                        fileRouteHandler,
                        webSocketRouteHandler
                    )
                })

            serverRunning.onNext(true)

            applicationEngine?.start(true)


        } catch (exception: Exception) {
            Log.d("Server", exception.message)
            //  applicationEngine.stop(3L, 0L, TimeUnit.SECONDS)

            emitter.onError(exception)

        } finally {

        }
        emitter.onComplete()

    }


    //notificationUseCase.showServerNotification()

    // vibrationUseCase.vibrate()

    fun stop(): Completable = Completable.create { emitter ->

        applicationEngine?.environment?.monitor?.subscribe(ApplicationStopPreparing) {
            Server.serverRunning.onNext(false)

            emitter.onComplete()
        }
        applicationEngine?.stop(3, 3, TimeUnit.SECONDS)

        notificationUseCase.cancelAll()
        Log.d("Server", "Server stopped")
        compositeDisposable.clear()

        vibrationUseCase.vibrate()


    }


    /****************************************** Class methods  */


    fun sendData(dataDestination: DataDestination, data: String) {
        when (dataDestination) {

            DataDestination.SHARE -> {
                webSocketRouteHandler.send(ShareItem(data))
            }
            else -> {
                throw NotImplementedError()
            }
        }
    }

    fun sendData(dataDestination: DataDestination, data: ByteArray) {
        when (dataDestination) {

            DataDestination.SHARE -> {
                throw NotImplementedError()
            }
            else -> {
                throw NotImplementedError()
            }
        }

    }


}

