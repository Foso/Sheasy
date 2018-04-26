package de.jensklingenberg.sheasy.network.service

import android.app.Service
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.os.Binder
import android.os.IBinder
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.extension.toFile
import de.jensklingenberg.sheasy.factories.ServerFactory
import de.jensklingenberg.sheasy.handler.AppsRequestHandler
import de.jensklingenberg.sheasy.helpers.MoshiHelper
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.model.DeviceResponse
import de.jensklingenberg.sheasy.toplevel.runInBackground
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.DeviceUtils
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.ResponseFile
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.OutgoingContent
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondFile
import io.ktor.response.respondText
import io.ktor.response.respondWrite
import io.ktor.routing.Routing.Feature.install
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.*

import java.io.*


/**
 * Created by jens on 25/2/18.
 */
class HTTPServerService : Service() {
    private val mBinder = ServiceBinder()
    private var serverImpl: MyHttpServer? = null
    private var app : App?= null
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
        app= App.instance



        runInBackground {
            embeddedServer(Netty, 8080) {
                routing {
                    /*     webSocket("/") { // websocketSession
                             while (true) {
                                 val frame = incoming.receive()
                                 when (frame) {
                                     is Frame.Text -> {
                                         val text = frame.readText()
                                         outgoing.send(Frame.Text("YOU SAID: $text"))
                                         if (text.equals("bye", ignoreCase = true)) {
                                             close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                                         }
                                     }
                                 }
                             }
                         }
     */

                    route("/"){

                    }



                   route(APIV1){
                       get("apps") {
                           val appsResponse = MoshiHelper.appsToJson(app!!.moshi, AppUtils.handleApps(app!!))

                           call.respondText(appsResponse, ContentType.Text.JavaScript)
                       }


                       route("web"){

                           get("index.html") {

                               call.respond(this@HTTPServerService.assets.open("web/index.html").readBytes())
                           }

                           get("/{param...}"){
                               val login: List<String>? = call.parameters.getAll("param")
                               call.respond(this@HTTPServerService.assets.open("web/index.html").readBytes())

                           }
                       }




                       get("device") {
                           App.instance.sendBroadcast("Device Info REQUESTED","Device")
                           val deviceInfo = DeviceUtils.getDeviceInfo()
                           val jsonAdapter = App.instance.moshi.adapter(DeviceResponse::class.java)


                           call.respondText(jsonAdapter?.toJson(deviceInfo)?:"", ContentType.Text.JavaScript)
                       }


                       get("bye") {
                           call.respondText("bye, world!", ContentType.Text.Html)
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

