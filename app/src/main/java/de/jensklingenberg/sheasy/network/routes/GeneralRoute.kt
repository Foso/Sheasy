package de.jensklingenberg.sheasy.network.routes

import de.jensklingenberg.sheasy.utils.FileRepository
import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.request.uri
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import model.ConnectionInfo

fun Route.general(
    futils: FileRepository
) {

    get("/") {
        call.respond(futils.returnAssetFile("web/index.html").readBytes())
    }

    get("web/{filepath...}") {
        var test = call.request.uri.replaceFirst("/", "")
        call.respond(futils.returnAssetFile(test).readBytes())
    }

    get("connect") {

        val deviceInfo = ConnectionInfo("OK", "Frist", emptyList())




        call.apply {
            response.header(HttpHeaders.AccessControlAllowOrigin, "*")
            respond(deviceInfo)
        }
    }
}