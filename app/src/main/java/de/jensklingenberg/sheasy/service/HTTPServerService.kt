package de.jensklingenberg.sheasy.service

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.IBinder
import androidx.annotation.RequiresApi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service() {


    companion object {
        val ACTION_ON_ACTIVITY_RESULT = "ACTION_ON_ACTIVITY_RESULT"
        val AUTHORIZE_DEVICE = "AUTHORIZE_DEVICE"

        private lateinit var bind: ServiceBinder
        private val ACTION_STOP = "ACTION_STOP"

        fun getIntent(context: Context) =
            Intent(context, HTTPServerService::class.java)

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
            } else {
                if (intent.hasExtra(AUTHORIZE_DEVICE)) {
                    val ipAddress = intent.getStringExtra(AUTHORIZE_DEVICE)
                    devicesDataSource.addAuthorizedDevice(
                        Device(
                            ipAddress,
                            authorizationType = AuthorizationType.AUTHORIZED
                        )
                    )
                }


            }
        }
        return START_STICKY
    }


    override fun onCreate() {
        super.onCreate()
        server.start()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground()
        else
            startForeground(1, Notification())
    }

    override fun stopService(name: Intent?): Boolean {
        stopForeground(true)
        server.stop()
        return super.stopService(name)

    }

    override fun onDestroy() {
        server.stop()
        super.onDestroy()
    }

    @RequiresApi(VERSION_CODES.O)
    private fun startMyOwnForeground() {
        startForeground(2, notificationUseCase.getForeGroundServiceNotification(this))
    }


}

