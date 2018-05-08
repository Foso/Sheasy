package de.jensklingenberg.sheasy.network.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.extension.getAudioManager
import de.jensklingenberg.sheasy.factories.ServerFactory
import de.jensklingenberg.sheasy.handler.MediaRequestHandler
import de.jensklingenberg.sheasy.helpers.MoshiHelper
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.model.DeviceResponse
import de.jensklingenberg.sheasy.toplevel.runInBackground
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.ContactUtils
import de.jensklingenberg.sheasy.utils.DeviceUtils
import de.jensklingenberg.sheasy.utils.MediatUtils
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
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

                            call.respondText(appsResponse, ContentType.Text.JavaScript)
                        }

                        get("contacts") {
                            val contacts = ContactUtils.readContacts(app!!.contentResolver)
                            val response = MoshiHelper.contactsToJson(app!!.moshi, contacts)
                            call.respondText(response, ContentType.Text.JavaScript)
                        }

                        route("media") {
                            val audioManager = app.getAudioManager()

                            get("louder") {

                                MediatUtils(audioManager).louder()
                                app.sendBroadcast(MediaRequestHandler.CATEGORY, "Media louder")
                                call.respondText("Louder", ContentType.Text.JavaScript)
                            }

                        }

                        get("device") {
                            App.instance.sendBroadcast("Device Info REQUESTED", "Device")
                            val deviceInfo = DeviceUtils.getDeviceInfo()
                            val jsonAdapter = App.instance.moshi.adapter(DeviceResponse::class.java)


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

