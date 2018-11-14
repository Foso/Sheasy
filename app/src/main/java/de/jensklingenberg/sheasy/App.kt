package de.jensklingenberg.sheasy

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import de.jensklingenberg.sheasy.di.AppComponent
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.di.DaggerAppComponent
import de.jensklingenberg.sheasy.di.ServiceModule
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod


/**
 * Created by jens on 9/2/18.
 */

class App : Application() {

    companion object {
        lateinit var oldAppComponent: AppComponent
    }


    fun initializeDagger() {
        oldAppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .serviceModule(ServiceModule())
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        initializeDagger()

    }


}

/**
 * KTOR APPLICATION
 */
fun io.ktor.application.Application.main() {
    install(DefaultHeaders) {
        header("X-Developer", "John Doe")
    }
    install(Compression) {
        gzip()
    }
    /*install(HttpsRedirect) {
        sslPort = 8443
    }*/
    install(PartialContent) {
        maxRangeCount = 10
    }

    install(CORS) {
        anyHost()
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.ContentType)
        allowCredentials = true
        listOf(
            HttpMethod.Get,
            HttpMethod.Put,
            HttpMethod.Delete,
            HttpMethod.Options
        ).forEach { method(it) }
    }


    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()


        }


    }


}