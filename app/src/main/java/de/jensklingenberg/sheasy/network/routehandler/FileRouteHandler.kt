package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.model.KtorApplicationCall
import de.jensklingenberg.model.Resource
import de.jensklingenberg.model.RouteHandler
import java.io.InputStream

interface FileRouteHandler : RouteHandler {
    suspend fun getAPK(call: KtorApplicationCall): Resource<Any>
    suspend fun getDownload(call: KtorApplicationCall): Resource<Any>
    suspend fun getShared(call: KtorApplicationCall): Resource<Any>
    fun postUpload(sourceFilePath: String, destinationFilePath: String, inputStream: InputStream): Resource<Any>

}
