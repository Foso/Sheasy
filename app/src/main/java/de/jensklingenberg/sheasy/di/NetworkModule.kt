package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.AndroidFileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.AndroidKtorGeneralRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketDataSource
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketRepository
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideGeneralRouteHandler(): GeneralRouteHandler =
        AndroidKtorGeneralRouteHandler()


    @Provides
    @Singleton
    fun provideFileRouteHandler(): FileRouteHandler =
        AndroidFileRouteHandler()

    @Provides
    @Singleton
    fun provideServer(): Server = Server()


    @Provides
    @Singleton
    fun provWebSocketDataSource(sheasyPreferences: SheasyPrefDataSource): NanoWSDWebSocketDataSource =
        NanoWSDWebSocketRepository(sheasyPreferences)


}