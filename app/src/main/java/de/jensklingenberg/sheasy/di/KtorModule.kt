package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.AndroidFileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.AndroidKtorGeneralRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import javax.inject.Singleton

@Module
class KtorModule {
    @Provides
    @Singleton
    fun provideGeneralRouteHandler(): GeneralRouteHandler =
        AndroidKtorGeneralRouteHandler()


    @Provides
    @Singleton
    fun provideFileRouteHandler(): FileRouteHandler =
        AndroidFileRouteHandler()



}