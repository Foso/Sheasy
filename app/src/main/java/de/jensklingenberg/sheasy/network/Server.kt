package de.jensklingenberg.sheasy.network

import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.data.usecase.VibrationUseCase
import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.network.ktor.routehandler.WebSocketRouteHandler
import io.ktor.server.engine.ApplicationEngine
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

    @Inject
    lateinit var applicationEngine: ApplicationEngine

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var webSocketRouteHandler: WebSocketRouteHandler

    init {
        initializeDagger()
    }


    /****************************************** Lifecycle methods  */


    private fun initializeDagger() = App.appComponent.inject(this)

    fun start(): Completable = Completable.create { emitter ->
        try {
            applicationEngine.start(wait = true)
            emitter.onComplete()

        } catch (exception: Exception) {
            Log.d("Server", exception.message)
            applicationEngine.stop(0L, 0L, TimeUnit.SECONDS)

            emitter.onError(exception)

        }

    }


    //notificationUseCase.showServerNotification()

    // vibrationUseCase.vibrate()

    fun stop() {
        applicationEngine.stop(0L, 0L, TimeUnit.SECONDS)

        serverRunning.onNext(false)

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

