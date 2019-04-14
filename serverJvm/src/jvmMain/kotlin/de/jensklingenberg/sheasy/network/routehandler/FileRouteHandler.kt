package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import io.reactivex.Single
import java.io.InputStream

interface FileRouteHandler : RouteHandler {
    fun getApps(): Single<List<AppInfo>>
    suspend fun apk( packageName:String): Resource<Any>
    suspend fun getDownload(filePath : String): Resource<Any>
    suspend fun getShared(call: KtorApplicationCall): Resource<Any>
    fun postUpload(sourceFilePath: String, destinationFilePath: String, inputStream: InputStream): Resource<Any>

}
