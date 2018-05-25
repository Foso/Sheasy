package de.jensklingenberg.sheasy.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RemoteModule {



    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()

    }



}