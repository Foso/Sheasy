package de.jensklingenberg.sheasy.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import javax.inject.Inject


class MainViewModel @Inject constructor() : ViewModel()
     {

    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    fun startService(intent: Intent) {

        application.startService(intent)


    }


    fun stopService(intent: Intent) {


        application.stopService(intent)

    }



}
