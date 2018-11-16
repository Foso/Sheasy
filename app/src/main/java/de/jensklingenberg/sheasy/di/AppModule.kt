package de.jensklingenberg.sheasy.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.SheasyPreferences
import de.jensklingenberg.sheasy.utils.extension.notificationManager
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager =
        context.notificationManager()

    @Provides
    @Singleton
    fun provideApp(): App = application

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun providePackageManager(context: Context): PackageManager = context.packageManager


    @Provides
    @Singleton
    fun provideSharedPreferences(application: App): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(application)


    @Provides
    @Singleton
    fun provideSheasyPreferences(): SheasyPreferences =
        SheasyPreferences()


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()


    @Provides
    @Singleton
    fun provideMoshiConverter() = MoshiConverterFactory.create()


}