package de.jensklingenberg.sheasy.web.model

interface RouteHandler{
    suspend fun get(call: KtorApplicationCall): Resource<Any>
}