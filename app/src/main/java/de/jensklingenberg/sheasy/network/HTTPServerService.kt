package de.jensklingenberg.sheasy.network

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.ui.common.OnResultActivity
import de.jensklingenberg.sheasy.utils.ScreenRecord
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import de.jensklingenberg.sheasy.web.model.Device
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service() {


    companion object {


        lateinit var bind: ServiceBinder
        fun getIntent(context: Context) =
            Intent(context, HTTPServerService::class.java)

        fun stopIntent(context: Context) =
            Intent(context, HTTPServerService::class.java).apply {
                action = ACTION_STOP
            }

        val ACTION_ON_ACTIVITY_RESULT = "ACTION_ON_ACTIVITY_RESULT"
        val AUTHORIZE_DEVICE = "AUTHORIZE_DEVICE"
        val serverRunning: BehaviorSubject<Boolean> = BehaviorSubject.create<Boolean>()
        private  val ACTION_STOP="ACTION_STOP"

        fun autorizeDeviceIntent(context: Context,ipaddress: String): Intent {
            val authIntent=  Intent(context, HTTPServerService::class.java).apply {
                putExtra(AUTHORIZE_DEVICE, ipaddress)
            }
            return authIntent
        }
    }

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var server: Server


    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource

    /****************************************** Lifecycle methods  */


    init {
        initializeDagger()
        bind = ServiceBinder()
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
                    sheasyPref.devicesRepository.addAuthorizedDevice(Device(ipAddress))
                }
                return super.onStartCommand(intent, flags, startId)

            }
        }
        return START_STICKY


    }


    override fun onCreate() {
        super.onCreate()

        val filter = IntentFilter()
        filter.addAction(ACTION_ON_ACTIVITY_RESULT) //further more
        registerReceiver(receiver, filter)

        notificationUseCase.showServerNotification()
        server.start()
        serverRunning.onNext(true)
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d("this", "Server stopped")

        server.stop()

        return super.stopService(name)

    }

    override fun onDestroy() {
        unregisterReceiver(receiver)

        serverRunning.onNext(false)

        server.stop()

        super.onDestroy()
    }

    /****************************************** Listener methods  */




    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ACTION_ON_ACTIVITY_RESULT) {
                val resultCode = intent.extras.getInt(OnResultActivity.RESULT_CODE)


                //action for sms received
            }
        }
    }

}

