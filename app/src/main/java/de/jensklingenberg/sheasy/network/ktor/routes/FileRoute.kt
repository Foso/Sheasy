package de.jensklingenberg.sheasy.network.ktor.routes

import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.ktor.toTo
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.utils.extension.debugCorsHeader
import de.jensklingenberg.sheasy.web.model.Resource
import de.jensklingenberg.sheasy.web.model.checkState
import io.ktor.application.call
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import java.io.InputStream


fun Route.handleFile(fileRouteHandler: FileRouteHandler) {
    route("file") {

        get("apps") {
            fileRouteHandler.getApps().await().run {
                call.response.debugCorsHeader()
                call.respond(Resource.success(this))
            }

        }

        param("apk") {
            get {
                val packageName = call.parameters
                val resource = fileRouteHandler.apk(HttpMethod.GET,call.toTo())
                resource.checkState(onError = {
                    launch {
                        call.response.header(
                            HttpHeaders.ContentDisposition,
                            ContentDisposition.Attachment.withParameter(
                                ContentDisposition.Parameters.FileName,
                                "$packageName.apk"
                            ).toString()
                        )
                        call.respond(resource.data!!)
                    }
                })
            }
        }

        param("download") {
            get {
                val resource = fileRouteHandler.getDownload(call.toTo())
                resource.checkState(onError = {
                    launch {
                        call.respond(resource)
                    }
                })
            }
        }

        param("shared") {
            get {
                val filePath = call.parameters["shared"] ?: ""
                val toTo = call.toTo("shared").apply {
                    parameter = filePath
                }
                val resource = fileRouteHandler.getShared(toTo)
                resource.checkState(onError = {
                    launch {
                        call.response.debugCorsHeader()
                        call.respond(resource)
                    }
                })
            }
        }

        param("upload") {
            post {
                val filePath = call.parameters["upload"] ?: ""

                var resource = Resource.error("", "")
                val multipart = call.receiveMultipart()
                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {

                        }
                        is PartData.FileItem -> {
                            part.streamProvider().use { its: InputStream ->
                                fileRouteHandler.postUpload(filePath, filePath, its)
                            }
                        }
                    }
                }

                resource.checkState(onError = {
                    launch {
                        call.respond(resource)
                    }
                })
            }
        }
    }

}
