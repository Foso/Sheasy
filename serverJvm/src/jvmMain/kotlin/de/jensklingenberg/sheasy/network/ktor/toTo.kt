package de.jensklingenberg.sheasy.network.ktor

import io.ktor.application.ApplicationCall
import io.ktor.features.origin

public fun ApplicationCall.toTo(apiPath:String=""): KtorApplicationCall {


    return KtorApplicationCall(
        remoteHostIp = this.request.origin.remoteHost,
        requestedApiPath = apiPath


    )


}

