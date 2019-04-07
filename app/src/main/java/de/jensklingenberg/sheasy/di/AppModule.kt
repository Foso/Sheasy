package de.jensklingenberg.sheasy.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.event.EventRepository
import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.data.notification.NotificationRepository
import de.jensklingenberg.sheasy.data.preferences.SheasyPreferencesRepository
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.utils.ScreenRecord
import javax.inject.Singleton

@Module
open class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application


    @Provides
    @Singleton
    fun provideApplication(): Application = application


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provScreenRecord(): ScreenRecord =
        ScreenRecord()

    @Provides
    @Singleton
    fun provideSheasyPrefDataSource(application: Application): SheasyPrefDataSource = SheasyPreferencesRepository(application)

    @Provides
    @Singleton
    open fun provideFileDataSource(): FileDataSource = FileRepository()

    @Provides
    @Singleton
    open fun provideNotificationDataSource(): NotificationDataSource =
        NotificationRepository()

    @Provides
    @Singleton
    open fun provideEventDataSource(): EventDataSource = EventRepository()

}