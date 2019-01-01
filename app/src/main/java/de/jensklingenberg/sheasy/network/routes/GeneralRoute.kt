package de.jensklingenberg.sheasy.network.routes

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.launch
import de.jensklingenberg.sheasy.web.model.checkState
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler


fun Route.general(
    generalRouteHandler: GeneralRouteHandler
) {

    get("/") {
        val resource =generalRouteHandler.get(call.toTo("/"))
        resource.checkState(onSuccess = {
            launch {
                call.respond(resource)
            }
        })
    }

    get("web/{filepath...}") {
        val resource =generalRouteHandler.get(Converter.toTo(call,"web/{filepath...}"))
        resource.checkState(onSuccess = {
            launch {
                call.respond(resource)
            }
        })
    }

}
