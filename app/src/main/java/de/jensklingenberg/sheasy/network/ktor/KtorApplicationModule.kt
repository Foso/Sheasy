package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.network.ktor.routes.general
import de.jensklingenberg.sheasy.network.ktor.routes.handleFile
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.PartialContent
import io.ktor.features.gzip
import io.ktor.gson.gson
import io.ktor.routing.route
import io.ktor.routing.routing
import repository.SheasyPrefDataSource


fun Application.ktorApplicationModule(
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

        routing {


            route("") {
                general(generalRouteHandler)

                route(sheasyPref.APIV1) {
                    handleFile(fileRouteHandler)
                }
            }
        }


    }

}