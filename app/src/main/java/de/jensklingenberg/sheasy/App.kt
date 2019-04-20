package de.jensklingenberg.sheasy

import android.app.Application
import de.jensklingenberg.sheasy.di.*


open class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    open fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .useCaseModule(UseCaseModule())
            .build()
    }

}
