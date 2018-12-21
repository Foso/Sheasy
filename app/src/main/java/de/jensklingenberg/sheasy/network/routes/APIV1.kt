package de.jensklingenberg.sheasy.network.routes

import de.jensklingenberg.sheasy.data.file.FileDataSource
import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import kotlinx.coroutines.rx2.await
import model.AppResponse
import model.Response

fun Route.apps(fileDataSource: FileDataSource) {
    get("apps") {
        fileDataSource.getApps()
            .await()
            .map {
                AppResponse(it.name, it.packageName, it.installTime)
            }.run {
                call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                call.respond(Response.success(this))
            }
    }
}

fun Route.screenshare(fileDataSource: FileDataSource) {
    get("screenshare") {
        fileDataSource.getApps()
            .await()
            .map {
                AppResponse(it.name, it.packageName, it.installTime)
            }.run {
                call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")
                call.respond(this)
            }
    }
}
