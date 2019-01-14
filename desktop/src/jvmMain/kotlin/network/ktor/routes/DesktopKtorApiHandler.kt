package network.ktor.routes

import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.checkState
import de.jensklingenberg.sheasy.network.routehandler.KtorApiHandler
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.param
import io.ktor.routing.route
import kotlinx.coroutines.launch
import main.MockTestDataSource
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.isMultipart
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.request.receiveStream
import io.ktor.response.respondTextWriter
import io.ktor.routing.post
import java.io.File
import java.io.InputStream

class DesktopKtorApiHandler: KtorApiHandler {
    override fun Route.file(fileRouteHandler: FileRouteHandler) {


        route("/file") {

            get("apps") {
                call.respond(Resource.success(MockTestDataSource.mockAppList))
            }


            route("shared") {
                get {
                //    call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                    call.respond(Resource.success(MockTestDataSource.sharedFolders))
                }
                param("upload") {
                    post {
                        val filePath = call.parameters["upload"] ?: ""

                        val multipart = call.receiveMultipart()
                        multipart.forEachPart { part ->
                            when (part) {
                                is PartData.FormItem -> {
                                    val tt = ("FormItem: ${part.name} = ${part.value}")

                                }
                                is PartData.FileItem -> {
                                    val tt = ("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                                    val ext = File(part.originalFileName).extension

                                    val sourceFile = File(filePath+part.originalFileName)

                                    part.streamProvider().use { its ->
                                        its.copyTo(sourceFile.outputStream())
                                        its.close()
                                        var fileExists = File(filePath+part.originalFileName+ext).exists()
                                        if(fileExists){
                                            call.respond(Resource.success("Filewrite okay"))
                                        }
                                    }
                                }

                            }

                        }

                    }
                }


            }



            param("shared") {
                get {
                    val filePath = call.parameters["shared"] ?: ""
                    val toTo = call.ktorApplicationCall("shared").apply {

                        parameter=filePath
                    }
                    val resource= fileRouteHandler.getShared( toTo)
                    resource.checkState(onError = {
                        launch {

                            call.respond(resource)
                        }
                    }, onSuccess = {
                        launch {

                            call.respond(resource)
                        }
                    })
                }
            }



        }


    }

}