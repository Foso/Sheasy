package network.routes

import io.ktor.application.ApplicationCall
import io.ktor.features.origin
import de.jensklingenberg.sheasy.web.model.KtorApplicationCall

class Converter{
    companion object {

        public fun toTo(gg: ApplicationCall, apiPath:String=""): KtorApplicationCall {
            return KtorApplicationCall(
                remoteHostIp = gg.request.origin.remoteHost,
                requestedApiPath = apiPath
            )


        }


    }
}
