package de.jensklingenberg.sheasy.utils.extension

import de.jensklingenberg.sheasy.BuildConfig
import io.ktor.http.HttpHeaders
import io.ktor.response.ApplicationResponse
import io.ktor.response.header


fun ApplicationResponse.debugCorsHeader() {
    if (BuildConfig.DEBUG) {
        this.header(
            HttpHeaders.AccessControlAllowOrigin,
            "*"
        )
    }

}