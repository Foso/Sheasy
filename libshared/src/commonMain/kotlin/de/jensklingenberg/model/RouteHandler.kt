package de.jensklingenberg.model

interface RouteHandler{
    suspend fun get(call: KtorApplicationCall): Resource<Any>
}