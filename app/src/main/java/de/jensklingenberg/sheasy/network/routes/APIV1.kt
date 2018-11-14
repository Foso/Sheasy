package de.jensklingenberg.sheasy.network.routes

import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.legacy.utils.AppUtils
import de.jensklingenberg.sheasy.legacy.utils.toplevel.respondWithJSON
import io.ktor.application.call
import io.ktor.http.HttpHeaders
import io.ktor.response.header
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.apps(appUtils: AppUtils, moshi: Moshi) {
    get("apps") {
        call.apply {
            response.header(HttpHeaders.AccessControlAllowOrigin, "*")
        }

        call.respondWithJSON(moshi, appUtils.getAppsResponseList2())

    }
}

