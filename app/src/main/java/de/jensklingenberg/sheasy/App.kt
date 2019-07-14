package de.jensklingenberg.sheasy

import android.app.Application
import android.util.Log
import de.jensklingenberg.sheasy.di.*
import io.reactivex.plugins.RxJavaPlugins


open class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
        RxJavaPlugins.setErrorHandler { throwable -> Log.e("Sheasy", "RxError" + throwable.message) }
    }

    open fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .useCaseModule(UseCaseModule())
            .build()
    }

}
