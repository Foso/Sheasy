package network.ktor.routes

import de.jensklingenberg.sheasy.network.routehandler.KtorApiHandler
import de.jensklingenberg.sheasy.network.ktor.toTo
import de.jensklingenberg.sheasy.web.model.Resource
import de.jensklingenberg.sheasy.web.model.checkState
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

class DesktopKtorApiHandler: KtorApiHandler {
    override fun Route.file(fileRouteHandler: FileRouteHandler) {


        route("/file") {

            get("apps") {
                call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                call.respond(Resource.success(MockTestDataSource.mockAppList))
            }



            param("shared") {
                get {
                    val filePath = call.parameters["shared"] ?: ""
                    val toTo = call.toTo("shared").apply {

                        parameter=filePath
                    }
                    val resource= fileRouteHandler.getShared( toTo)
                    resource.checkState(onError = {
                        launch {
                            call.response.header(
                                HttpHeaders.AccessControlAllowOrigin,
                                "*"
                            )
                            call.respond(resource)
                        }
                    },onSuccess = {
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



        }


    }

}