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
import de.jensklingenberg.sheasy.model.DeviceResponse
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

data class ConnectionInfo(val result: String, val deviceName: String)


class HTTPServerService : Service(), NanoWsdWebSocketListener {


    @Inject
    lateinit var moshi: Moshi

    val app by lazy { App.instance }
    private val mBinder = ServiceBinder()
    private var serverImpl: MyHttpServer? = null
    private val APIV1 = "/api/v1/"
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

        serverImpl = ServerFactory.createHTTPServer(this)
        serverImpl?.start(10000)

        runInBackground {
            serv = embeddedServer(Netty, App.port) {
                routing {
                    get("/") {
                        app.sendBroadcast(
                            EventCategory.CONNECTION,
                            "from IP:" + call.request.origin.host
                        )

                        call.respond(this@HTTPServerService.assets.open("web/index.html").readBytes())
                    }



                    get("swagger") {
                        app.sendBroadcast(EventCategory.REQUEST, "swagger")

                        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

                        call.respond(this@HTTPServerService.assets.open("swagger/SwaggerUI.html").readBytes())


                    }

                    get("swagger/{filepath...}") {
                        var test = call.request.uri.replaceFirst("/", "")
                        Log.d("SWAGGER", test)
                        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

                        call.respond(this@HTTPServerService.assets.open("swagger/" + test).readBytes())

                    }


                    get("web/{filepath...}") {
                        var test = call.request.uri.replaceFirst("/", "")
                        call.respond(this@HTTPServerService.assets.open(test).readBytes())
                    }


                    route(APIV1) {
                        get("apps") {
                            app.sendBroadcast(EventCategory.REQUEST, "/apps")

                            val appsResponse =
                                moshi.toJson(AppUtils.getAppsResponseList(app))


                            call.apply {
                                response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                                respondText(appsResponse, ContentType.Text.JavaScript)
                            }


                        }

                        get("connect") {
                            app.sendBroadcast(
                                EventCategory.REQUEST,
                                "Device Info REQUESTED"
                            )
                            val deviceInfo = ConnectionInfo("OK", "Frist")


                            call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                            call.respondText(
                                moshi?.toJson(deviceInfo) ?: "",
                                ContentType.Text.JavaScript
                            )
                        }

                        get("contacts") {
                            app.sendBroadcast(EventCategory.REQUEST, "/contacts")

                            val contacts = ContactUtils.readContacts(app.contentResolver)
                            val response = moshi.toJson(contacts)
                            call.respondText(response, ContentType.Text.JavaScript)
                        }

                        route("file") {


                            param("apk") {
                                get {
                                    val login = call.parameters["apk"] ?: ""

                                    val test = FUtils.returnAPK(app, login)
                                    val mimeType = test?.mimeType
                                    val fileInputStream = test?.fileInputStream

                                    call.respond(fileInputStream?.readBytes() ?: "apk Not found")
                                }

                            }

                            param("upload") {
                                post {
                                    val filePath = call.parameters["upload"] ?: ""


                                    val multipart = call.receiveMultipart()
                                    multipart.forEachPart { part ->
                                        when (part) {
                                            is PartData.FormItem -> {

                                            }
                                            is PartData.FileItem -> {
                                                val ext = File(part.originalFileName).extension

                                                val sourceFile = File(filePath)
                                                val destinationFile = File(filePath)
                                                sourceFile.copyTo(destinationFile, true)

                                                part.streamProvider().use { its ->
                                                    its.copyTo(sourceFile.outputStream())
                                                }
                                            }

                                        }

                                    }
                                }
                            }


                            param("download") {
                                get {
                                    val login = call.parameters["download"] ?: ""

                                    if (login.contains(".")) {

                                        val test = FUtils.returnAPK(app, login)
                                        val mimeType = test?.mimeType
                                        val fileInputStream = FileInputStream(File(login))

                                        call.respond(
                                            fileInputStream.readBytes()
                                        )
                                    } else {
                                        app.sendBroadcast("FilePath Requested", login)

                                        val fileList = FUtils.getFilesReponseList(login)

                                        if (fileList.isEmpty()) {
                                            call.respondText(
                                                "path not found",
                                                ContentType.Text.JavaScript
                                            )

                                        } else {


                                            call.apply {
                                                response.header(
                                                    HttpHeaders.AccessControlAllowOrigin,
                                                    "*"
                                                )
                                                respondText(
                                                    moshi.toJson(fileList),
                                                    ContentType.Text.JavaScript
                                                )
                                            }

                                        }


                                    }

                                }
                            }
                        }

                        route("media") {
                            val audioManager = app.getAudioManager()

                            get("louder") {
                                app.sendBroadcast(EventCategory.MEDIA, "louder")

                                MediaUtils(audioManager).louder()
                                app.sendBroadcast(EventCategory.MEDIA, "Media louder")
                                call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

                                call.respondText("Louder", ContentType.Text.JavaScript)
                            }

                            get("pause") {
                                app.sendBroadcast(EventCategory.MEDIA, "puase")

                                KeyUtils.sendKeyEvent(app, KeyEvent.KEYCODE_MEDIA_PAUSE)
                                app.sendBroadcast(EventCategory.MEDIA, "Media pause")

                                call.respondText("Pause", ContentType.Text.JavaScript)
                            }

                        }

                        get("device") {
                            App.instance.sendBroadcast(
                                EventCategory.REQUEST,
                                "Device Info REQUESTED"
                            )
                            val deviceInfo = DeviceUtils.getDeviceInfo()


                            call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                            call.respondText(
                                moshi?.toJson(deviceInfo) ?: "",
                                ContentType.Text.JavaScript
                            )
                        }
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

