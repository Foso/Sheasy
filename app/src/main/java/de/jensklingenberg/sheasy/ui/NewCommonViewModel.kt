package de.jensklingenberg.sheasy.ui

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import model.FileResponse
import javax.inject.Inject


class NewCommonViewModel @Inject constructor() : ViewModel(),
    ServiceConnection {


    lateinit var service: IBinder

    @Inject
    lateinit var application: Application

    override fun onServiceDisconnected(name: ComponentName?) {}

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        service?.let {
            this.service = service
        }
    }


    var folderPath: MutableLiveData<String> = MutableLiveData()
    val defaultPath = "/storage/emulated/0/"


    var files: MutableLiveData<List<FileResponse>> = MutableLiveData()


    init {
        initializeDagger()
        folderPath.value = defaultPath
    }

    private fun initializeDagger() = App.oldAppComponent.inject(this)


    fun startService(intent: Intent) {

        application.startService(intent)


    }


    fun stopService(intent: Intent) {


        application.stopService(intent)

    }


    companion object {

    }


}
