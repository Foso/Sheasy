package de.jensklingenberg.sheasy.network.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BackportWebSocket
import de.jensklingenberg.sheasy.factories.ServerFactory
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.network.service.apiv1.*
import de.jensklingenberg.sheasy.network.websocket.NanoWsdWebSocketListener
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.DefaultHeaders
import io.ktor.features.PartialContent
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import org.threeten.bp.Duration
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */


class HTTPServerService : Service(), NanoWsdWebSocketListener {


    @Inject
    lateinit var moshi: Moshi
    val APIV1 = "/api/v1/"
    val TEST = "TEST"

    val app by lazy { App.instance }
    private var serverImpl: MyHttpServer? = null
    var serv: NettyApplicationEngine? = null

    var sharedFolder = ArrayList<String>()


    fun setShared(string: String) {
        sharedFolder.add(string)
    }

    companion object {
        lateinit var bind: ServiceBinder
    }


    init {
        initializeDagger()
        bind = ServiceBinder(this)


    }

    private fun initializeDagger() = App.appComponent.inject(this)


    class ServiceBinder(val httpServerService: HTTPServerService) : Binder()


    override fun onBind(p0: Intent?): IBinder {
        return bind
    }


    override fun onNotificationWebSocketRequest() {


    }

    fun io.ktor.application.Application.main() {
        install(DefaultHeaders)
        install(PartialContent) {
            maxRangeCount = 10
        }

        install(CORS) {
            anyHost()
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            listOf(
                HttpMethod.Get,
                HttpMethod.Put,
                HttpMethod.Delete,
                HttpMethod.Options
            ).forEach { method(it) }
        }

        install(BackportWebSocket) {
            pingPeriod = Duration.ofSeconds(60) // Disabled (null) by default
            timeout = Duration.ofSeconds(15)
            maxFrameSize =
                    Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
            masking = false
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            if (it.hasExtra(TEST)) {
                Log.d("THIS", "YES")
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onCreate() {
        super.onCreate()
        //  sharedFolder.add("/storage/emulated/0/")

        runInBackground {
            serv = embeddedServer(Netty, App.port) {
                routing {
                    general(app, moshi)

                    route(APIV1) {
                        apps(app, moshi)
                        file(app, moshi, this@HTTPServerService)
                        media(app, moshi)
                        device(moshi)
                        contacts(app, moshi)
                    }
                }
            }
            serv?.start(wait = true)


        }

        try {

            serverImpl = ServerFactory.createHTTPServer(this)
            serverImpl?.start(10000)
            Log.i("TAG", "Server is started: " + serverImpl?.getHostname())

        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    override fun stopService(name: Intent?): Boolean {
        serv?.stop(0L, 0L, TimeUnit.SECONDS)
        serverImpl?.stop()
        return super.stopService(name)

    }

    override fun onDestroy() {
        serv?.stop(0L, 0L, TimeUnit.SECONDS)
        serverImpl?.stop()
        Log.d("hhh", "ddd")
        super.onDestroy()
    }


}

