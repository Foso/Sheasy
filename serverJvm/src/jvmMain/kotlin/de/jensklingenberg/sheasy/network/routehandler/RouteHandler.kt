package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.web.model.Resource

interface RouteHandler{
     fun get(call: KtorApplicationCall): Resource<Any>
}