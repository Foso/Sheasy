package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.utils.NotificationUtils
import javax.inject.Singleton

@Module
class UtilsModule {


    @Provides
    @Singleton
    fun provideNotifUtils() = NotificationUtils()


    @Provides
    @Singleton
    fun provideFUtils() :FileDataSource= MockFileRepository()


}