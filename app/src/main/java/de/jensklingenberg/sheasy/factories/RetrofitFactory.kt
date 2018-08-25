package de.jensklingenberg.sheasy.factories

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.api.SheasyAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitFactory {

    @Inject
    lateinit var moshi: MoshiConverterFactory

    @Inject
    lateinit var rxJava2CallAdapterFactory: RxJava2CallAdapterFactory


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    fun createRetrofit(ip: String): SheasyAPI {
        return Retrofit.Builder()
            .addConverterFactory(moshi)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(ip)
            .build().create(SheasyAPI::class.java)
    }

}