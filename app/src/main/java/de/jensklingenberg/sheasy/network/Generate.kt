package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.network.routes.general
import de.jensklingenberg.sheasy.network.routes.toTo
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.PartialContent
import io.ktor.features.gzip
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.launch
import de.jensklingenberg.sheasy.web.model.checkState
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import io.ktor.application.Application
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.header
import io.ktor.routing.*
import de.jensklingenberg.sheasy.web.model.Resource
import repository.SheasyPrefDataSource
import java.io.InputStream


fun Route.handleFile(fileRouteHandler: FileRouteHandler) {
    route("file") {

        get("apps") {
            val resource = fileRouteHandler.get(call.toTo("apps"))
            resource.checkState(
                onError = {
                    launch {
                        call.respond(resource)
                    }
                },
                onSuccess = {
                    launch {
                        call.respond(resource)
                    }
                }
            )
        }

        param("apk") {
            get {
                val packageName = call.parameters
                val resource = fileRouteHandler.getAPK(call.toTo())
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
                        call.response.header(
                            HttpHeaders.AccessControlAllowOrigin,
                            "*"
                        )
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


fun Application.module2(
    sheasyPref: SheasyPrefDataSource,
    generalRouteHandler: GeneralRouteHandler,
    fileRouteHandler: FileRouteHandler
) {
    with(this) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        install(Compression) {
            gzip()
        }
        install(PartialContent) {
            maxRangeCount = 10
        }

        routing{
            route("") {

                intercept(ApplicationCallPipeline.Call) {
                    val resource = generalRouteHandler.intercept(call.toTo())
                    resource.checkState(onError = {
                        launch {
                            call.respond(resource)
                        }
                    })
                }

                general(generalRouteHandler)

                route(sheasyPref.APIV1) {
                    handleFile(fileRouteHandler)
                }
            }
        }



    }

}


fun initNetty(
    sheasyPref: SheasyPrefDataSource,
    generalRouteHandler: GeneralRouteHandler,
    fileRouteHandler: FileRouteHandler
): NettyApplicationEngine {
    return embeddedServer(Netty, port = sheasyPref.port, module = {
        module2(sheasyPref,generalRouteHandler,fileRouteHandler)
    })
}