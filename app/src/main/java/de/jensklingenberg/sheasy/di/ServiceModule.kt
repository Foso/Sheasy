package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.network.Server
import javax.inject.Singleton

@Module
class ServiceModule {


    @Provides
    @Singleton
    fun provideNettyApplicationEngine(): Server = Server()
}