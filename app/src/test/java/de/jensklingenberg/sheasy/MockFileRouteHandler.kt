package de.jensklingenberg.sheasy

import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.model.KtorApplicationCall
import de.jensklingenberg.model.Resource
import java.io.InputStream

class MockFileRouteHandler:FileRouteHandler {
    override suspend fun getAPK(call: KtorApplicationCall): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getDownload(call: KtorApplicationCall): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getShared(call: KtorApplicationCall): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun postUpload(
        sourceFilePath: String,
        destinationFilePath: String,
        inputStream: InputStream
    ): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun get(call: KtorApplicationCall): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}