package de.jensklingenberg.sheasy.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.SheasyPreferences
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.NotifUtils
import de.jensklingenberg.sheasy.network.MyHttpServerImpl
import io.ktor.server.netty.NettyApplicationEngine
import javax.inject.Singleton

@Module
class ServiceModule {


    @Provides
    @Singleton
    fun provideS(

        moshi: Moshi,

        sheasyPreferences: SheasyPreferences,
        notifUtils: NotifUtils,
        fUtils: FUtils
    ): NettyApplicationEngine =
        MyHttpServerImpl.getNetty(moshi, sheasyPreferences, notifUtils, fUtils)


}