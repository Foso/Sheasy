package de.jensklingenberg.sheasy.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val bueApplication: Application) {

  @Provides
  @Singleton
  fun provideContext(): Application = bueApplication




}