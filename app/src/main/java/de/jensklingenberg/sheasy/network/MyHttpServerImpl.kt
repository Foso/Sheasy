package de.jensklingenberg.sheasy.network


import android.content.Context
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.SheasyPreferences
import de.jensklingenberg.sheasy.utils.AppsRepository
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.network.routes.apps
import de.jensklingenberg.sheasy.network.routes.file
import de.jensklingenberg.sheasy.network.routes.general
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.features.origin
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import javax.inject.Inject


class MyHttpServerImpl {


    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var notificationUtils1: NotificationUtils


    init {
        initializeDagger()
        notificationUtils1.generateBundle()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    companion object {

        fun getNetty(
            moshi: Moshi,
            sheasyPref: SheasyPreferences,
            notificationUtils: NotificationUtils,
            futils: FUtils
        ): NettyApplicationEngine {

            val APIV1 = "/api/v1/"


            return embeddedServer(Netty, sheasyPref.port) {

                routing {
                    route("") {

                        intercept(ApplicationCallPipeline.Call) {

                            if (sheasyPref.authorizedDevices.contains(call.request.origin.remoteHost)) {

                            } else {
                                notificationUtils.showConnectionRequest(call.request.origin.remoteHost)
                                sheasyPref.addAuthorizedDevice(call.request.origin.remoteHost)

                            }


                        }


                        general(moshi, futils)

                        route(APIV1) {
                            apps(AppsRepository(), moshi)
                            file(AppsRepository(), moshi, sheasyPref, futils)

                        }
                    }


                }
            }
        }

    }


}



