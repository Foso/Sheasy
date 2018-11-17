package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.SheasyPreferences
import de.jensklingenberg.sheasy.network.getNetty
import de.jensklingenberg.sheasy.utils.NotificationUtils
import io.ktor.server.netty.NettyApplicationEngine
import javax.inject.Singleton

@Module
class ServiceModule {


    @Provides
    @Singleton
    fun provideNettyApplicationEngine(
        sheasyPreferences: SheasyPreferences,
        notificationUtils: NotificationUtils,
        fileDataSource: FileDataSource
    ): NettyApplicationEngine =
        getNetty(sheasyPreferences,notificationUtils,fileDataSource)


}