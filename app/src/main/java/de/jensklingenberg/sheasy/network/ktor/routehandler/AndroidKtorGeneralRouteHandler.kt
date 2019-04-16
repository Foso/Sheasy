package de.jensklingenberg.sheasy.network.ktor.routehandler

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.model.*
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import io.reactivex.Single
import java.io.InputStream
import javax.inject.Inject

class AndroidKtorGeneralRouteHandler : GeneralRouteHandler {


    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var eventDataSource: EventDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override suspend fun intercept(call: KtorApplicationCall): Resource<Any> {

        if (sheasyPref.acceptAllConnections) {


            if (sheasyPref.devicesRepository.authorizedDevices.none{device->device.ip.equals(call.remoteHostIp)}) {
                sheasyPref.devicesRepository.addAuthorizedDevice(Device(call.remoteHostIp,authorizationType = AuthorizationType.AUTHORIZED))
                eventDataSource.addEvent(Event(EventCategory.CONNECTION, call.remoteHostIp))

            }
            return Resource.success("1")

        }

        val allowedPath = sheasyPref.nonInterceptedFolders.any { folderPath ->
            call.requestedApiPath.startsWith(folderPath)
        }

        if (allowedPath) {
            return Resource.success("1")

        }


        if (!sheasyPref.devicesRepository.authorizedDevices.contains(Device(call.remoteHostIp,authorizationType = AuthorizationType.AUTHORIZED))) {
            notificationUseCase.showConnectionRequest(call.remoteHostIp)
            eventDataSource.addEvent(Event(EventCategory.CONNECTION, call.remoteHostIp))
            return Resource.error(SheasyError.NotAuthorizedError())

        } else {

            return Resource.success("1")

        }

    }



    override fun getStartPage(): Single<InputStream> {
        return fileDataSource
            .getAssetFile("web/index.html")

    }

    override fun getConnectionPage(): Single<InputStream> {
        return fileDataSource
            .getAssetFile("web/connection/connection.html")
    }


    override fun getFile(filePath: String): Single<InputStream> {
        return fileDataSource
            .getAssetFile(filePath)

    }


}