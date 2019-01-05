package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.RouteHandler
import de.jensklingenberg.sheasy.web.model.AppInfo
import de.jensklingenberg.sheasy.web.model.Resource
import io.reactivex.Single
import java.io.InputStream

interface FileRouteHandler : RouteHandler {
    fun getApps(): Single<List<AppInfo>>
    suspend fun apk(httpMethod: HttpMethod, call: KtorApplicationCall): Resource<Any>
    suspend fun getDownload(call: KtorApplicationCall): Resource<Any>
    suspend fun getShared(call: KtorApplicationCall): Resource<Any>
    fun postUpload(sourceFilePath: String, destinationFilePath: String, inputStream: InputStream): Resource<Any>

}
