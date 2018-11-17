package de.jensklingenberg.sheasy.network.routes

import de.jensklingenberg.sheasy.data.FileDataSource
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get


fun Route.general(
    fileDataSource: FileDataSource
) {

    get("/") {
        call.respond(fileDataSource.returnAssetFile("web/index.html").readBytes())
    }

    get("web/{filepath...}") {
        val filepath = "web/" + call.parameters["filepath"]
        call.respond(fileDataSource.returnAssetFile(filepath).readBytes())
    }

}