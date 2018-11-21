package de.jensklingenberg.sheasy.network.routes

import de.jensklingenberg.sheasy.data.file.FileDataSource
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.rx2.await


fun Route.general(
    fileDataSource: FileDataSource
) {

    get("/") {
        fileDataSource
            .returnAssetFile("web/index.html")
            .map { it.readBytes() }
            .await()
            .run {
                call.respond(this)
            }
    }

    get("web/{filepath...}") {
        val filepath = "web/" + call.parameters["filepath"]

        fileDataSource
            .returnAssetFile(filepath)
            .map { it.readBytes() }
            .await()
            .run {
                call.respond(this)

            }
    }

}