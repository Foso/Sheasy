package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.widget.PopupMenu
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.interfaces.ApiEventListener
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.FUtils
import javax.inject.Inject


class ProfileViewModel @Inject constructor(val application2: Application) : ViewModel(),
    ApiEventListener {
    
    var shareMessage: MutableLiveData<ArrayList<Event>> = MutableLiveData()

    var sharedFolder: MutableLiveData<String> = MutableLiveData()

    var files: MutableLiveData<List<FileResponse>> = MutableLiveData()
    var apps: MutableLiveData<List<AppsResponse>> = MutableLiveData()


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    fun getFiles(folderPath: String) {
        files.value = FUtils.getFilesReponseList(folderPath)
    }

    fun showPopup(context: Context?, v: View) {
        context?.let {
            PopupMenu(context, v).apply {
                inflate(R.menu.actions)
                show()
            }
        }

    }


    fun getApps() {
        apps.value = AppUtils.getAppsResponseList(application2)
    }

    override fun onShare(test: Event) {
        events.add(test)
        shareMessage.value = events
    }


    fun setSharedFolder(string: String) {
        sharedFolder.value = string
    }

    fun startService(intent: Intent) {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_SHARE)
        val mySharedMessageBroadcastReceiver = App.instance.mySharedMessageBroadcastReceiver
        mySharedMessageBroadcastReceiver.apiEventListener = this

        application2.registerReceiver(mySharedMessageBroadcastReceiver, filter)
        application2.startService(intent)


    }

    fun stopService(intent: Intent) {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_SHARE)
        val tt = App.instance.mySharedMessageBroadcastReceiver
        tt.apiEventListener = this


        application2.registerReceiver(tt, filter)
        application2.stopService(intent)

    }


    companion object {
        var events = arrayListOf<Event>()


    }


}
