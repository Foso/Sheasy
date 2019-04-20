package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import io.ktor.routing.Route
import io.reactivex.Single
import java.io.FileInputStream
import java.io.InputStream

interface FileRouteHandler : RouteHandler {


    fun handleRoute(route: Route)

}
