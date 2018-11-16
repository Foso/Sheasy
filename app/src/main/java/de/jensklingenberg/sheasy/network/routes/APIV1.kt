package de.jensklingenberg.sheasy.network.routes

import de.jensklingenberg.sheasy.utils.IAppsRepostitoy
import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.apps(appsRepository: IAppsRepostitoy) {
    get("apps") {
        call.apply {
            response.header(HttpHeaders.AccessControlAllowOrigin, "*")
        }

        call.respond(appsRepository.getApps())

    }
}

