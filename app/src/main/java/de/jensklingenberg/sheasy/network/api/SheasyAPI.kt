package de.jensklingenberg.sheasy.network.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import de.jensklingenberg.sheasy.model.ConnectionInfo
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface SheasyAPI {

    @GET("connect")
    fun connect(): Single<Response<List<ConnectionInfo>>>


}