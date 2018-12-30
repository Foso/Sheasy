package network.routehandler

import de.jensklingenberg.model.Resource
import de.jensklingenberg.model.KtorApplicationCall
import de.jensklingenberg.model.RouteHandler


interface GeneralRouteHandler : RouteHandler {
    suspend fun intercept(call: KtorApplicationCall): Resource<Any>

}

