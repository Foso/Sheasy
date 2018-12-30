package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.network.ktor.KtorFileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.KtorGeneralRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import network.routehandler.AppRouteHandler
import javax.inject.Singleton

@Module
class KtorModule {
    @Provides
    @Singleton
    fun provideGeneralRouteHandler(): GeneralRouteHandler =
        KtorGeneralRouteHandler()


    @Provides
    @Singleton
    fun provideFileRouteHandler(): FileRouteHandler =
        KtorFileRouteHandler()



}