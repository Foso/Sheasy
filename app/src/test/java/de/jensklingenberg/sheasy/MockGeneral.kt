package de.jensklingenberg.sheasy

import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.model.KtorApplicationCall
import de.jensklingenberg.model.Resource

class MockGeneral : GeneralRouteHandler {
    override suspend fun intercept(call: KtorApplicationCall): Resource<Any> {
        return Resource.error("Could not intercept", "")

    }

    override suspend fun get(call: KtorApplicationCall): Resource<Any> {
        return Resource.error("Could not intercept", "")
    }
}