package de.jensklingenberg.sheasy.di

import android.net.wifi.WifiManager
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.data.devices.DevicesRepository
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyApi
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.ktor.ktorApplicationModule
import de.jensklingenberg.sheasy.network.ktor.routehandler.AndroidFileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.routehandler.AndroidKtorGeneralRouteHandler
import de.jensklingenberg.sheasy.network.ktor.routehandler.WebSocketRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketDataSource
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketRepository
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {
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
    fun provideWebSocketRouteHandler(): WebSocketRouteHandler =
        WebSocketRouteHandler()


    @Provides
    @Singleton
    open fun provideServer(): Server = Server()

    @Provides
    @Singleton
    open fun provideDevicesDataSource(): DevicesDataSource = DevicesRepository()


    @Provides
    @Singleton
    fun provWebSocketDataSource(sheasyPreferences: SheasyPrefDataSource): NanoWSDWebSocketDataSource =
        NanoWSDWebSocketRepository(sheasyPreferences)


    @Provides
    @Singleton
    fun provideRetrofit(wm: WifiManager) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://localhost:8766/")
        .build()


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(SheasyApi::class.java)

    @Provides
    @Singleton
    open fun provideNettyApplicationEngine(
        sheasyPrefDataSource: SheasyPrefDataSource,
        generalRouteHandler: GeneralRouteHandler,
        fileRouteHandler: FileRouteHandler,
        webSocketRouteHandler: WebSocketRouteHandler
    ): ApplicationEngine =
        embeddedServer(
            Netty,
            port = sheasyPrefDataSource.httpPort.toInt(),
            module = {
            ktorApplicationModule(
                generalRouteHandler,
                fileRouteHandler,
                webSocketRouteHandler
            )
        })

}