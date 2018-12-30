package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.data.file.FileRepository

import de.jensklingenberg.sheasy.utils.NotificationUtils
import javax.inject.Singleton

@Module
class UtilsModule {




    @Provides
    @Singleton
    fun provideNotifUtils() = NotificationUtils()

    @Provides
    @Singleton
    fun provideFUtils(): FileDataSource = FileRepository()


}