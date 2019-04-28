package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.network.ktor.routehandler.WebSocketRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.PartialContent
import io.ktor.features.gzip
import io.ktor.gson.gson
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.websocket.WebSockets


/**
 * This is the configuration for the Ktor Server
 *
 */
fun Application.ktorApplicationModule(
    generalRouteHandler: GeneralRouteHandler,
    fileRouteHandler: FileRouteHandler,
    webSocketRouteHandler: WebSocketRouteHandler
) {
    with(this) {
        //  install(DefaultHeaders)

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()

            }
        }
        install(WebSockets) {

        }

        install(Compression) {
            gzip()
        }
        install(PartialContent) {
            maxRangeCount = 10
        }


        routing {
            route("") {
                generalRouteHandler.handleRoute(this)

                webSocketRouteHandler.websocket(this)


                route("/api/v1/file/") {
                    fileRouteHandler.handleRoute(this)
                }


            }
        }

    }

}