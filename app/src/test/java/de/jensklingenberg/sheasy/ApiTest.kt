package de.jensklingenberg.sheasy

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.serjltt.moshi.adapters.FallbackOnNull
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.model.ConnectionInfo
import de.jensklingenberg.sheasy.model.Event

import de.jensklingenberg.sheasy.network.api.SheasyAPI
import de.jensklingenberg.sheasy.utils.extension.toJson
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

import org.w3c.dom.Element
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.ArrayList

@RunWith(MockitoJUnitRunner::class)
class ApiTest {
    private lateinit var api: SheasyAPI
    private var retrofit: Retrofit? = null
    private var moshi: Moshi? = null

    private val baseurl = "http://192.168.2.35:8766/"

    private fun setupRetrofit() {

        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseurl)
            .build()

        api = retrofit!!.create(SheasyAPI::class.java)
    }

    private fun setupMoshi() {
        moshi = Moshi.Builder().add(FallbackOnNull.ADAPTER_FACTORY).build()
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setupMoshi()
        setupRetrofit()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @Test
    fun checkMoshi() {
        val connectionInfo = Event(EventCategory.REQUEST, "ttt2")
        println(moshi?.toJson(connectionInfo))
    }

    @Test
    fun checkConnectResponse() {

        val observer = api.connect().test()
        observer.assertNoErrors()
        val response = observer.values()[0].body()
        System.out.println(response?.size)

        Assert.assertEquals(response?.get(0)?.result, "OK")

    }


}

