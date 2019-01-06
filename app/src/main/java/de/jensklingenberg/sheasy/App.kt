package de.jensklingenberg.sheasy

import android.app.Application
import de.jensklingenberg.sheasy.di.AppComponent
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.di.DaggerAppComponent
import de.jensklingenberg.sheasy.di.NetworkModule
import de.jensklingenberg.sheasy.di.UseCaseModule


class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .useCaseModule(UseCaseModule())
            .build()
    }

}
