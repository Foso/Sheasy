package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import repository.SheasyPrefDataSource


fun initNetty(
    sheasyPrefDataSource: SheasyPrefDataSource,
    generalRouteHandler: GeneralRouteHandler,
    fileRouteHandler: FileRouteHandler
): NettyApplicationEngine {
    return embeddedServer(Netty, port = sheasyPrefDataSource.httpPort, module = {
        ktorApplicationModule(sheasyPrefDataSource, generalRouteHandler, fileRouteHandler)
    })
}