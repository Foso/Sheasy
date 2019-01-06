package de.jensklingenberg.sheasy.network.routehandler

import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import io.reactivex.Single
import java.io.File
import java.io.InputStream


interface GeneralRouteHandler : RouteHandler {
    suspend fun intercept(call: KtorApplicationCall): Resource<Any>
    fun getStartPage(): Single<InputStream>
    fun getFile(filePath:String): Single<InputStream>

}

