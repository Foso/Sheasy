package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.utils.NotificationUtils
import kotlinx.coroutines.rx2.await
import de.jensklingenberg.sheasy.web.model.Device
import de.jensklingenberg.sheasy.web.model.Resource
import io.ktor.application.call
import io.ktor.response.respond
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import repository.SheasyPrefDataSource
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class AndroidKtorGeneralRouteHandler : GeneralRouteHandler {



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

    override fun get(call: KtorApplicationCall): Resource<Any> {
        return Resource.error("","")

    }

    override fun getStartPage(): Single<InputStream> {
        return fileDataSource
            .getAssetFile("web/index.html")

    }

    override fun getFile(filePath: String): Single<InputStream> {
        return fileDataSource
            .getAssetFile(filePath)

    }


}