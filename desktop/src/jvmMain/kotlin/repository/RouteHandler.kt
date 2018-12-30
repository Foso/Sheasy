package repository

import de.jensklingenberg.model.KtorApplicationCall
import de.jensklingenberg.model.Resource

interface RouteHandler{
    suspend fun get(call: KtorApplicationCall): Resource<Any>
}