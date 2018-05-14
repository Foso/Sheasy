package de.jensklingenberg.sheasy.network.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.getAudioManager
import de.jensklingenberg.sheasy.factories.ServerFactory
import de.jensklingenberg.sheasy.handler.MediaRequestHandler
import de.jensklingenberg.sheasy.helpers.MoshiHelper
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.model.DeviceResponse
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.toplevel.runInBackground
import de.jensklingenberg.sheasy.utils.*
import io.ktor.application.call
import io.ktor.content.PartData
import io.ktor.content.forEachPart
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.request.receiveMultipart
import io.ktor.request.uri
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException


/**
 * Created by jens on 25/2/18.
 */
class HTTPServerService : Service() {
    private val mBinder = ServiceBinder()
    private var serverImpl: MyHttpServer? = null
    private val app by lazy { App.instance }
    private val APIV1 = "/api/v1/"

    inner class ServiceBinder : Binder() {
        val playerService: HTTPServerService
            get() = this@HTTPServerService
    }


    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }


    override fun onCreate() {
        super.onCreate()
        //app = App.instance


        serverImpl = ServerFactory.createHTTPServer(this)
        serverImpl?.start(10000)



        runInBackground {
            embeddedServer(Netty, 8766) {


                routing {


                    webSocket("ws/message") {
                        // websocketSession
                        while (true) {
                            val frame = incoming.receive()
                            when (frame) {
                                is Frame.Text -> {
                                    val text = frame.readText()
                                    outgoing.send(Frame.Text("YOU SAID: $text"))
                                    if (text.equals("bye", ignoreCase = true)) {
                                        close(
                                            CloseReason(
                                                CloseReason.Codes.NORMAL,
                                                "Client said BYE"
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }


                    get("/") {
                        call.respond(this@HTTPServerService.assets.open("web/index.html").readBytes())
                    }


                    get("web/{filepath...}") {
                        var test = call.request.uri.replaceFirst("/", "")
                        call.respond(this@HTTPServerService.assets.open(test).readBytes())

                    }


                    route(APIV1) {
                        get("apps") {
                            val appsResponse =
                                MoshiHelper.appsToJson(app!!.moshi, AppUtils.handleApps(app!!))

                            call.apply {
                                response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                                respondText(appsResponse, ContentType.Text.JavaScript)
                            }


                        }

                        get("contacts") {
                            val contacts = ContactUtils.readContacts(app!!.contentResolver)
                            val response = MoshiHelper.contactsToJson(app!!.moshi, contacts)
                            call.respondText(response, ContentType.Text.JavaScript)
                        }

                        route("file") {


                            param("apk") {
                                get() {
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
                                            fileInputStream?.readBytes() ?: "download Not found"
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
                                            val moshi = Moshi.Builder().build()
                                            val listMyData = Types.newParameterizedType(
                                                List::class.java,
                                                FileResponse::class.java
                                            )
                                            val adapter =
                                                moshi.adapter<List<FileResponse>>(listMyData)
                                                    .toJson(fileList)

                                            call.apply {
                                                response.header(
                                                    HttpHeaders.AccessControlAllowOrigin,
                                                    "*"
                                                )
                                                respondText(adapter, ContentType.Text.JavaScript)
                                            }

                                        }


                                    }

                                }

                            }


                            // call.respondText(response, ContentType.Text.JavaScript)
                        }

                        route("media") {
                            val audioManager = app.getAudioManager()

                            get("louder") {

                                MediatUtils(audioManager).louder()
                                app.sendBroadcast(MediaRequestHandler.CATEGORY, "Media louder")
                                call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

                                call.respondText("Louder", ContentType.Text.JavaScript)
                            }

                        }

                        get("device") {
                            App.instance.sendBroadcast("Device Info REQUESTED", "Device")
                            val deviceInfo = DeviceUtils.getDeviceInfo()
                            val jsonAdapter = App.instance.moshi.adapter(DeviceResponse::class.java)


                            call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                            call.respondText(
                                jsonAdapter?.toJson(deviceInfo) ?: "",
                                ContentType.Text.JavaScript
                            )
                        }
                    }

                }
            }.start(wait = true)
        }

        try {

            // serverImpl = ServerFactory.createHTTPServer(this)
            //  serverImpl?.start(10000)
            Log.i("TAG", "Server is started: " + serverImpl?.getHostname())

        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    override fun stopService(name: Intent?): Boolean {
        serverImpl?.stop()
        return super.stopService(name)

    }


}

