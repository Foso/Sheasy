package de.jensklingenberg.sheasy

import android.app.Application
import android.content.Intent
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.data.viewmodel.SettingsViewModel
import de.jensklingenberg.sheasy.di.AppComponent
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.di.DaggerAppComponent
import de.jensklingenberg.sheasy.di.RemoteModule
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.utils.ApplicationUtils
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.DefaultHeaders
import io.ktor.features.PartialContent
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import org.threeten.bp.Duration
import javax.inject.Inject


/**
 * Created by jens on 9/2/18.
 */

class App : Application() {


    // @Inject
    //lateinit var moshi: Moshi


    val mySharedMessageBroadcastReceiver = MySharedMessageBroadcastReceiver()


    companion object {
        lateinit var instance: App
        var port = 0
        lateinit var appComponent: AppComponent

    }


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
        initializeDagger()
        // SettingsViewModel.savePort(this,8766)

        port = SettingsViewModel.loadPort(this)
    }


    fun sendBroadcast(event: Event) {
        ApplicationUtils(this).sendBroadcast(event)

    }


    fun sendBroadcast(category: EventCategory, text: String) {
        ApplicationUtils(this).sendBroadcast(category, text)

    }


    @Deprecated("Use EventCategory")
    fun sendBroadcast(category: String, text: String) {
        ApplicationUtils(this).sendBroadcast(category, text)
    }


}