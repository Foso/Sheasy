package de.jensklingenberg.sheasy.network.service

import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.model.ConnectionInfo
import de.jensklingenberg.sheasy.utils.extension.toJson
import io.ktor.application.call
import io.ktor.features.origin
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.request.uri
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.general(app: App, moshi: Moshi) {
    get("/") {
        app.sendBroadcast(
            EventCategory.CONNECTION,
            "from IP:" + call.request.origin.host
        )

        call.respond(app.assets.open("web/index.html").readBytes())
    }



    get("swagger") {
        app.sendBroadcast(EventCategory.REQUEST, "swagger")

        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

        call.respond(app.assets.open("swagger/SwaggerUI.html").readBytes())


    }

    get("swagger/{filepath...}") {
        var test = call.request.uri.replaceFirst("/", "")
        Log.d("SWAGGER", test)
        call.response.header(HttpHeaders.AccessControlAllowOrigin, "*")

        call.respond(app.assets.open("swagger/" + test).readBytes())

    }


    get("web/{filepath...}") {
        var test = call.request.uri.replaceFirst("/", "")
        call.respond(app.assets.open(test).readBytes())
    }

    get("connect") {
        app.sendBroadcast(
            EventCategory.REQUEST,
            "Device Info REQUESTED"
        )
        val deviceInfo = ConnectionInfo("OK", "Frist", emptyList())




        call.apply {
            response.header(HttpHeaders.AccessControlAllowOrigin, "*")
            respondText(
                moshi.toJson(listOf(deviceInfo)),
                ContentType.Text.JavaScript
            )
        }
    }
}