package de.jensklingenberg.sheasy.network.routehandler

import io.ktor.routing.Route
import io.reactivex.Single
import java.io.InputStream


interface GeneralRouteHandler : RouteHandler {
    companion object {
        val STARTPAGE_PATH = "web/index.html"
        val CONNECTION_PAGE = "web/connection/connection.html"
    }


    fun handleRoute(route: Route)
}

