package de.jensklingenberg.sheasy.network.routehandler

import io.ktor.routing.Route
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler

interface KtorApiHandler{
    fun Route.file(fileRouteHandler: FileRouteHandler)
}