package de.jensklingenberg.sheasy

import android.app.Application
import android.content.Intent
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.model.Event
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.websocket.WebSockets
import org.threeten.bp.Duration
import android.content.SharedPreferences
import de.jensklingenberg.sheasy.data.viewmodel.SettingsViewModel
import de.jensklingenberg.sheasy.di.AppComponent
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.di.DaggerAppComponent
import de.jensklingenberg.sheasy.di.RemoteModule
import de.jensklingenberg.sheasy.enums.EventCategory


/**
 * Created by jens on 9/2/18.
 */

class App : Application() {

    var moshi: Moshi = Moshi.Builder().build()

    val mySharedMessageBroadcastReceiver = MySharedMessageBroadcastReceiver()

    init {


    }

    fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
           // .serviceModule(ServiceModule())
            .remoteModule(RemoteModule())
            .build()
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)

       // SettingsViewModel.savePort(this,8766)

        port = SettingsViewModel.loadPort(this)
    }

    fun io.ktor.application.Application.main() {
        install(DefaultHeaders)
        install(CORS) {
            anyHost()
            header(HttpHeaders.AccessControlAllowOrigin)
            allowCredentials = true
            listOf(
                HttpMethod.Get,
                HttpMethod.Put,
                HttpMethod.Delete,
                HttpMethod.Options
            ).forEach { method(it) }
        }
        /*   install(WebSockets){

        }
     install(BackportWebSocket) {
            pingPeriod = Duration.ofSeconds(60) // Disabled (null) by default
            timeout = Duration.ofSeconds(15)
            maxFrameSize =
                    Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
            masking = false
        }*/

    }

    companion object {
        lateinit var instance: App
        var port = 0
        lateinit var appComponent: AppComponent

    }

    fun sendBroadcast(event: Event) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(MySharedMessageBroadcastReceiver.ACTION_SHARE, event)
        }
        sendBroadcast(pipp)

    }



    fun sendBroadcast(category: EventCategory, text: String) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(MySharedMessageBroadcastReceiver.ACTION_SHARE, Event(category, text))
        }
        sendBroadcast(pipp)

    }


   @Deprecated("") fun sendBroadcast(category: String, text: String) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(MySharedMessageBroadcastReceiver.ACTION_SHARE, Event(EventCategory.DEFAULT, text))
        }
        sendBroadcast(pipp)

    }


}