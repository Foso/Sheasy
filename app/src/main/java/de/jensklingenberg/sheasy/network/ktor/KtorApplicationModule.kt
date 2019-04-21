package de.jensklingenberg.sheasy.network.ktor

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import de.jensklingenberg.sheasy.model.AndroidAppInfo
import de.jensklingenberg.sheasy.model.AppInfo
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
import java.lang.reflect.Type
import android.R.attr.src
import android.R.id
import com.google.gson.*


/**
 * This is the configuration for the Ktor Server
 *
 */
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

        routing {
            route("") {
                generalRouteHandler.handleRoute(this)

                route("/api/v1/") {
                    fileRouteHandler.handleRoute(this)
                }
            }
        }

    }

}