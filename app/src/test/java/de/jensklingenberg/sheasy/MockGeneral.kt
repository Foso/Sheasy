package de.jensklingenberg.sheasy

import de.jensklingenberg.sheasy.web.model.KtorApplicationCall
import de.jensklingenberg.sheasy.model.Resource

class MockGeneral : GeneralRouteHandler {
    override suspend fun intercept(call: KtorApplicationCall): Resource<Any> {
        return Resource.error("Could not intercept", "")

    }

    override suspend fun get(call: KtorApplicationCall): Resource<Any> {
        return Resource.error("Could not intercept", "")
    }
}