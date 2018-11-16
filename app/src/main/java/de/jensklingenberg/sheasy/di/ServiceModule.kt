package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.SheasyPreferences
import de.jensklingenberg.sheasy.network.getNetty
import de.jensklingenberg.sheasy.utils.FileRepository
import de.jensklingenberg.sheasy.utils.IAppsRepostitoy
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
        fileRepository: FileRepository,
        iAppsRepostitoy: IAppsRepostitoy
    ): NettyApplicationEngine =
        getNetty(sheasyPreferences,notificationUtils,fileRepository,iAppsRepostitoy)


}