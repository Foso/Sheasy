package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.web.model.Resource
import de.jensklingenberg.sheasy.web.model.KtorApplicationCall
import de.jensklingenberg.sheasy.web.model.RouteHandler


interface GeneralRouteHandler : RouteHandler {
    suspend fun intercept(call: KtorApplicationCall): Resource<Any>

}

