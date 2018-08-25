package de.jensklingenberg.sheasy.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.jensklingenberg.sheasy.network.api.ChangeableBaseUrlInterceptor
import de.jensklingenberg.sheasy.network.api.SheasyAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {


    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder().build()


    @Provides
    @Singleton
    fun provideCallAdapter() = RxJava2CallAdapterFactory.create()


    @Provides
    @Singleton
    fun provideMoshiConverter() = MoshiConverterFactory.create()


    @Provides
    @Singleton
    fun provideHostSelectionInterceptor() = ChangeableBaseUrlInterceptor()

    @Provides
    @Singleton
    fun provideSheasyApi(retrofit: Retrofit) = retrofit.create(SheasyAPI::class.java)


    @Provides
    @Singleton
    fun provideOkHttpClient(hostSelectionInterceptor: ChangeableBaseUrlInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(hostSelectionInterceptor)
            .build();


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit = Retrofit.Builder()

        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .baseUrl("http://example.com")
        .build()


}