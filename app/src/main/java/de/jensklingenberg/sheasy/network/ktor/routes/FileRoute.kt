package de.jensklingenberg.sheasy.network.ktor.routes

import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.checkState
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.utils.extension.debugCorsHeader
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
import java.io.File


fun Route.handleFile(fileRouteHandler: FileRouteHandler) {
    route("file") {


        route("app") {
            param("package") {
                get {
                    val packageName = call.parameters["package"] ?: ""
                    fileRouteHandler
                        .apk(HttpMethod.GET, packageName)
                        .checkState(onSuccess = { resource ->
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
        }

        route("file") {
            param("path") {
                get {
                    val filepath = call.parameters["path"] ?: ""
                    fileRouteHandler.getDownload(filepath)
                        .checkState(onSuccess = {
                            launch {
                                call.response.header(
                                    HttpHeaders.ContentDisposition,
                                    ContentDisposition.Attachment.withParameter(
                                        ContentDisposition.Parameters.FileName,
                                        value = filepath.substringAfterLast(delimiter = "/")
                                    ).toString()
                                )
                                call.respond(it.data!!)
                            }
                        })
                }

            }
        }



        get("apps") {
            fileRouteHandler
                .getApps()
                .await()
                .run {
                    call.response.debugCorsHeader()
                    call.respond(Resource.success(this))
                }

        }



        route("shared") {
            get {
                //    call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                val filePath = call.parameters["folder"] ?: ""


                val ktor = call.ktorApplicationCall(filePath).apply {
                    parameter = filePath
                }

                fileRouteHandler
                    .getShared(ktor)
                    .checkState(onSuccess = {
                        launch {
                            call.response.debugCorsHeader()
                            call.respond(it)
                        }
                    }, onError = {
                        launch {
                            call.response.debugCorsHeader()
                            call.respond(it)
                        }
                    })
            }
            param("upload") {
                post {
                    val filePath2 = call.parameters["upload"] ?: ""

                    val tt = call
                    val filePath = filePath2

                    val multipart = call.receiveMultipart()
                    multipart.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                val tt = ("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                                val ext = File(part.originalFileName).extension

                                val sourceFile = File(filePath + part.originalFileName)

                                part.streamProvider().use { its ->
                                    its.copyTo(sourceFile.outputStream())
                                    its.close()
                                    var fileExists = File(filePath + part.originalFileName).exists()
                                    if (fileExists) {
                                        call.response.debugCorsHeader()

                                        call.respond(Resource.success("Filewrite okay"))
                                    } else {
                                        call.response.debugCorsHeader()

                                        call.respond(Resource.error(Error.UploadFailedError().message, ""))
                                    }
                                }
                            }

                            else -> {

                            }
                        }

                    }

                }
            }


        }
    }

}
