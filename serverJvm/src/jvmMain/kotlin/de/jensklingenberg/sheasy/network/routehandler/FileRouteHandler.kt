package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import io.reactivex.Single
import java.io.InputStream

interface FileRouteHandler : RouteHandler {
    fun apps(httpMethod: HttpMethod): Single<List<AppInfo>>
    suspend fun apk(httpMethod: HttpMethod, call: KtorApplicationCall): Resource<Any>
    suspend fun getDownload(call: KtorApplicationCall): Resource<Any>
    suspend fun getShared(call: KtorApplicationCall): Resource<Any>
    fun postUpload(sourceFilePath: String, destinationFilePath: String, inputStream: InputStream): Resource<Any>

}
