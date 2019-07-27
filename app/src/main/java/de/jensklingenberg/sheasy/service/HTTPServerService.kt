package de.jensklingenberg.sheasy.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.data.notification.NotificationProvider
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service() {


    companion object {
        val ACTION_ON_ACTIVITY_RESULT = "ACTION_ON_ACTIVITY_RESULT"
        val ACTION_STOP_SERVER = "ACTION_STOP_SERVER"
        val ACTION_START_SERVER = "ACTION_START_SERVER"

        val AUTHORIZE_DEVICE = "AUTHORIZE_DEVICE"


        const val NOTIFICAITON_ID = 0


        private lateinit var bind: ServiceBinder
        private val ACTION_STOP = "ACTION_STOP"

        fun getStartIntent(context: Context) =
            Intent(context, HTTPServerService::class.java).apply {
                action = ACTION_START_SERVER
            }

        fun stopIntent(context: Context) =
            Intent(context, HTTPServerService::class.java).apply {
                action = ACTION_STOP
            }

        fun authorizeDeviceIntent(context: Context, ipaddress: String): Intent {
            return Intent(context, HTTPServerService::class.java).apply {
                putExtra(AUTHORIZE_DEVICE, ipaddress)
            }
        }
    }


    @Inject
    lateinit var server: Server


    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource


    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var devicesDataSource: DevicesDataSource

    @Inject
    lateinit var notificationManager: NotificationManager

    val compositeDisposable = CompositeDisposable()


    /****************************************** Lifecycle methods  */


    init {
        initializeDagger()
        bind =
            ServiceBinder()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onBind(p0: Intent?): IBinder {
        return ServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            if (it.action?.equals(ACTION_STOP) == true) {
                stopService(intent)

                return START_STICKY
            } else if (it.action?.equals(ACTION_STOP_SERVER) == true) {
                stopService(intent)

                return START_STICKY
            } else if (it.action?.equals(ACTION_START_SERVER) == true) {
                startServ()

                return START_STICKY
            } else {
                if (intent.hasExtra(AUTHORIZE_DEVICE)) {
                    val ipAddress = intent.getStringExtra(AUTHORIZE_DEVICE)
                    devicesDataSource.addAuthorizedDevice(
                        Device(
                            ipAddress,
                            authorizationType = AuthorizationType.AUTHORIZED
                        )
                    )
                    notificationManager.cancel(NotificationProvider.NOTIFICATION_CHANNEL_ID_CONNECTION_REQUEST_ID)
                }


            }
        }
        return START_STICKY
    }

    private fun startServ() {
        server
            .start()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    Log.d("Server", "Server running")
                    Server.serverRunning.onNext(true)


                },

                onError = {
                    Log.d("Server", it.message)
                    Server.serverRunning.onNext(false)

                }).addTo(compositeDisposable)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(
                NotificationProvider.NOTIFICATION_CHANNEL_ID_SERVER_STATE_ID,
                notificationUseCase.getForeGroundServiceNotification(this)
            )
        } else
            startForeground(
                NotificationProvider.NOTIFICATION_CHANNEL_ID_SERVER_STATE_ID,
                notificationUseCase.getForeGroundServiceNotification(this)
            )

    }


    override fun onCreate() {

    }

    override fun stopService(name: Intent?): Boolean {
        compositeDisposable.dispose()
        server.stop()

        stopForeground(true)
         super.stopService(name)
        return true

    }

    override fun onDestroy() {
        server.stop()
        super.onDestroy()
    }


}

