package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.utils.NotificationUtils
import javax.inject.Singleton

@Module
class ServiceModule {


    @Provides
    @Singleton
    fun provideNettyApplicationEngine(
        sheasyPrefDataSource: SheasyPrefDataSource,
        notificationUtils: NotificationUtils
    ): Server =
        Server()


}