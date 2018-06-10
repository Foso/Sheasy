package de.jensklingenberg.sheasy.network.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BackportWebSocket
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.factories.ServerFactory
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.model.ConnectionInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.service.apiv1.file
import de.jensklingenberg.sheasy.network.service.apiv1.media
import de.jensklingenberg.sheasy.network.websocket.NanoWsdWebSocketListener
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import de.jensklingenberg.sheasy.utils.*
import de.jensklingenberg.sheasy.utils.extension.getAudioManager
import de.jensklingenberg.sheasy.utils.extension.toJson
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.PartData
import io.ktor.content.forEachPart
import io.ktor.features.CORS
import io.ktor.features.DefaultHeaders
import io.ktor.features.PartialContent
import io.ktor.features.origin
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.request.receiveMultipart
import io.ktor.request.uri
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import org.threeten.bp.Duration
import java.io.File
import java.io.FileInputStream
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


    val app by lazy { App.instance }
    private val mBinder = ServiceBinder()
    private var serverImpl: MyHttpServer? = null
    var serv: NettyApplicationEngine? = null

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    inner class ServiceBinder : Binder() {
        val playerService: HTTPServerService
            get() = this@HTTPServerService
    }


    override fun onBind(p0: Intent?): IBinder {
        return mBinder
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


    override fun onCreate() {
        super.onCreate()

        runInBackground {
            serv = embeddedServer(Netty, App.port) {
                routing {
                    general(app, moshi)

                    route(APIV1) {
                        apps(app, moshi)
                        file(app, moshi)
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

