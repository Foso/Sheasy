package de.jensklingenberg.sheasy.network.ktor.routehandler

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import de.jensklingenberg.sheasy.web.model.Device
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

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override suspend fun intercept(call: KtorApplicationCall): Resource<Any> {

        if(sheasyPref.acceptAllConnections){
            return Resource.success("1")

        }

        val allowedPath = sheasyPref.nonInterceptedFolders.any { folderPath ->
            call.requestedApiPath.startsWith(folderPath)
        }

        if(allowedPath){
            return Resource.success("1")

        }


        if (!sheasyPref.devicesRepository.authorizedDevices.contains(Device(call.remoteHostIp))) {
            notificationUseCase.showConnectionRequest(call.remoteHostIp)
            return Resource.error(Error.NotAuthorizedError())

        } else {

            return Resource.success("1")

        }

    }

    override fun get(call: KtorApplicationCall): Resource<Any> {
        return Resource.error("", "")

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