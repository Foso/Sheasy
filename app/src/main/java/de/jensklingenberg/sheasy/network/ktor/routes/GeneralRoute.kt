package de.jensklingenberg.sheasy.network.ktor.routes

import de.jensklingenberg.sheasy.model.checkState
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await


fun Route.general(
    generalRouteHandler: GeneralRouteHandler
) {

      intercept(ApplicationCallPipeline.Call) {
                      val resource = generalRouteHandler.intercept(call.ktorApplicationCall())
                      resource.checkState(onError = {
                          launch {
                             // call.respond(resource)
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
        val filepath = "web/" + call.parameters["filepath"]

        generalRouteHandler.getFile(filepath)
            .map { it.readBytes() }
            .await()
            .run {
                call.respond(this)

            }
    }

}
