package repository

import de.jensklingenberg.sheasy.web.model.KtorApplicationCall
import de.jensklingenberg.sheasy.web.model.Resource

interface RouteHandler{
    suspend fun get(call: KtorApplicationCall): Resource<Any>
}