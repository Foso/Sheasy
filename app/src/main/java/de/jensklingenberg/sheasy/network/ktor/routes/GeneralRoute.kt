package de.jensklingenberg.sheasy.network.ktor.routes

import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.model.checkState
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.http.cio.websocket.Frame
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await


fun Route.general(
    generalRouteHandler: GeneralRouteHandler
) {

    intercept(ApplicationCallPipeline.Call) {

        val filepath = "web/" + call.parameters.entries().firstOrNull { it.key == "filepath" }?.value?.joinToString("/")

        val resource = generalRouteHandler.intercept(call.ktorApplicationCall(filepath))
        resource.checkState(onError = {
            launch {


                if (it.message.equals(SheasyError.NotAuthorizedError().message)) {

                    if (!filepath.contains("web/connection/")) {
                        generalRouteHandler.getConnectionPage()
                            .map { it.readBytes() }
                            .await()
                            .run {
                                call.respond(this)
                            }
                    }


                }
            }
        })
    }

    get("/") {
        generalRouteHandler.getStartPage()
            .map { it.readBytes() }
            .await()
            .run {
                call.respond(this)
            }
    }

    get("web/{filepath...}") {
        // val filepath = "web/" + call.parameters["filepath"]
        val filepath = "web/" + call.parameters.entries().first { it.key == "filepath" }.value.joinToString("/")

        generalRouteHandler.getFile(filepath)
            .map { it.readBytes() }
            .await()
            .run {
                call.respond(this)

            }
    }

}
