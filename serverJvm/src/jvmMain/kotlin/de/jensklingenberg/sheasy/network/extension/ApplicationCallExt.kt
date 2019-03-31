package de.jensklingenberg.sheasy.network.extension

import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import io.ktor.application.ApplicationCall
import io.ktor.features.origin



public fun ApplicationCall.ktorApplicationCall(apiPath:String="", parameters: MutableMap<String,String> = mutableMapOf()): KtorApplicationCall {


    return KtorApplicationCall(
        remoteHostIp = this.request.origin.remoteHost,
        requestedApiPath = apiPath,
        parameters = parameters

    )


}