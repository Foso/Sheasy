package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.network.ktor.routes.general
import de.jensklingenberg.sheasy.network.ktor.routes.handleFile
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


fun Application.ktorApplicationModule(
    generalRouteHandler: GeneralRouteHandler,
    fileRouteHandler: FileRouteHandler
) {
    with(this) {
        //  install(DefaultHeaders)

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



        // anyHost()
        // header(HttpHeaders.AccessControlAllowOrigin)
        // allowCredentials = true


        routing {


            route("") {
                general(generalRouteHandler)

                route("/api/v1/") {
                    handleFile(fileRouteHandler)
                }
            }
        }


    }

}