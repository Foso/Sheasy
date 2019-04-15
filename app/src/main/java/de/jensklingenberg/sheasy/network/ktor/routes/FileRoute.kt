package de.jensklingenberg.sheasy.network.ktor.routes

import android.util.Log
import de.jensklingenberg.sheasy.model.SheasyError
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
import java.io.FileNotFoundException


fun Route.handleFile(fileRouteHandler: FileRouteHandler) {


    /**
     * GET
     */
    route("file") {
        get("apps") {
            //"file/apps"
            fileRouteHandler
                .getApps()
                .await()
                .run {
                    call.response.debugCorsHeader()
                    call.respond(Resource.success(this))
                }
        }

        route("app") {
            param("package") {
                get {
                    val packageName = call.parameters["package"] ?: ""
                    fileRouteHandler
                        .apk( packageName)
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
                                it.data?.let {
                                    call.respond(it)

                                }
                            }
                        })
                }

            }
        }

        route("shared") {
            get {
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

            /**
             * POST
             */
            param("upload") {
                post {
                    val filePath2 = call.parameters["upload"] ?: ""

                    val filePath = filePath2

                    val multipart = call.receiveMultipart()
                    multipart.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                val sourceFilePath = filePath + part.originalFileName

                                try {
                                    part.streamProvider().use { its ->


                                       fileRouteHandler.postUpload(sourceFilePath,sourceFilePath,its).checkState(onSuccess = {
                                           launch {
                                               call.response.debugCorsHeader()
                                               call.respond(it)
                                           }
                                       },onError = {
                                           launch {
                                               call.response.debugCorsHeader()
                                               call.respond(it)
                                           }
                                       })



                                    }
                                }catch (io:FileNotFoundException){
                                    Log.d("FileRoute: ",io.localizedMessage)
                                    call.response.debugCorsHeader()

                                    call.respond(Resource.error(SheasyError.UploadFailedError().message, ""))
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
