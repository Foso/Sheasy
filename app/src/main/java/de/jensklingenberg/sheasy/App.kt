package de.jensklingenberg.sheasy

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import de.jensklingenberg.sheasy.di.AppComponent
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.di.DaggerAppComponent
import de.jensklingenberg.sheasy.di.ServiceModule


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        initializeDagger()
    }

    fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .serviceModule(ServiceModule())
            .build()
    }

}
