package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.utils.NotificationUtils
import kotlinx.coroutines.rx2.await
import de.jensklingenberg.model.Device
import de.jensklingenberg.model.Resource
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.model.KtorApplicationCall
import repository.SheasyPrefDataSource
import javax.inject.Inject

class KtorGeneralRouteHandler : GeneralRouteHandler {


    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource

    @Inject
    lateinit var notificationUtils: NotificationUtils

    @Inject
    lateinit var fileDataSource: FileDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override suspend fun intercept(call: KtorApplicationCall): Resource<Any> {
        if (sheasyPref.acceptAllConnections) {
            if (!sheasyPref.authorizedDevices.contains(Device(call.remoteHostIp))) {
                val device = Device(call.remoteHostIp)
                sheasyPref.addAuthorizedDevice(device)
            }

        } else {
            if (sheasyPref.authorizedDevices.contains(Device(call.remoteHostIp))) {

            } else {

                notificationUtils.showConnectionRequest(call.remoteHostIp)
                return Resource.error("1", "")

            }
        }

        return Resource.error("Could not intercept", "")

    }


    override suspend fun get(call: KtorApplicationCall): Resource<Any> {
        when (call.requestedApiPath) {
            "/" -> {
                fileDataSource
                    .returnAssetFile("web/index.html")
                    .map { it.readBytes() }
                    .await()
                    .run {
                        Resource.success(this)
                        //  call.respond(this)
                    }
            }

            "web/{filepath...}" -> {
                val filepath = "web/" + call.parameter

                fileDataSource
                    .returnAssetFile(filepath)
                    .map { it.readBytes() }
                    .await()
                    .run {
                        Resource.success(this)

                        // call.respond(this)

                    }
            }
        }
        return Resource.error("", "")
    }


}