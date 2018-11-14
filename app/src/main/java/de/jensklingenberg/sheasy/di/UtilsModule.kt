package de.jensklingenberg.sheasy.di

import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.NotifUtils
import javax.inject.Singleton

@Module
class UtilsModule {


    @Provides
    @Singleton
    fun provideAppUtils() = AppUtils()

    @Provides
    @Singleton
    fun provideNotifUtils() = NotifUtils()

    @Provides
    @Singleton
    fun provideFUtils() = FUtils()


}