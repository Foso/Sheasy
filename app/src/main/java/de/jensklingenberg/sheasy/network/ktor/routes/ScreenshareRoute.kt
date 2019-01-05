package de.jensklingenberg.sheasy.network.ktor.routes

import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.utils.extension.debugCorsHeader
import de.jensklingenberg.sheasy.web.model.AppResponse
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.rx2.await


fun Route.screenshare(fileDataSource: FileDataSource) {
    get("screenshare") {
        fileDataSource.getApps()
            .await()
            .map {
                AppResponse(it.name, it.packageName, it.installTime)
            }.run {
                call.response.debugCorsHeader()
                call.respond(this)
            }
    }
}
