package de.jensklingenberg.sheasy.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.media.projection.MediaProjectionManager
import android.view.WindowManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.data.preferences.SheasyPreferencesRepository
import de.jensklingenberg.sheasy.utils.ScreenRecord
import de.jensklingenberg.sheasy.utils.extension.mediaProjectionManager
import de.jensklingenberg.sheasy.utils.extension.notificationManager
import de.jensklingenberg.sheasy.utils.extension.windowManager
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = application


    @Provides
    @Singleton
    fun provideApp(): App = application

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
    fun provideSheasyPrefDataSource(): SheasyPrefDataSource = SheasyPreferencesRepository()

    @Provides
    @Singleton
    fun provideFUtils(): FileDataSource = FileRepository()


}