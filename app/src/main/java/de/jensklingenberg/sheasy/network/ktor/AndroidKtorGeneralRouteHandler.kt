package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import de.jensklingenberg.sheasy.model.Resource
import io.reactivex.Single
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.web.model.Device
import java.io.InputStream
import javax.inject.Inject

class AndroidKtorGeneralRouteHandler : GeneralRouteHandler {



    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

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

                notificationUseCase.showConnectionRequest(call.remoteHostIp)
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