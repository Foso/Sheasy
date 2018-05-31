package de.jensklingenberg.sheasy.di

import android.app.Application
import android.widget.ViewSwitcher
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Application = application

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()

    }
}