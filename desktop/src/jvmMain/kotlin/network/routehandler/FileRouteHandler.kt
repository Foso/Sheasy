package network.routehandler

import de.jensklingenberg.sheasy.web.model.KtorApplicationCall
import de.jensklingenberg.sheasy.web.model.Resource
import de.jensklingenberg.sheasy.web.model.RouteHandler
import java.io.InputStream

interface FileRouteHandler : RouteHandler {
    suspend fun getAPK(call: KtorApplicationCall): Resource<Any>
    suspend fun getDownload(call: KtorApplicationCall): Resource<Any>
    suspend fun getShared(call: KtorApplicationCall): Resource<Any>
    fun postUpload(sourceFilePath: String, destinationFilePath: String, inputStream: InputStream): Resource<Any>

}
