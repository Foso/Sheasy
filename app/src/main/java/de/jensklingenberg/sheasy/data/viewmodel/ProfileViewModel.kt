package de.jensklingenberg.sheasy.data.viewmodel

import android.Manifest
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.interfaces.ApiEventListener
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.PermissionUtils.Companion.MY_PERMISSIONS_REQUEST_READ_CONTACTS


class ProfileViewModel(val application2: Application) : AndroidViewModel(application2),
    ApiEventListener {

    var shareMessage: MutableLiveData<ArrayList<Event>> = MutableLiveData()

    var sharedFolder: MutableLiveData<String> = MutableLiveData()

    var files: MutableLiveData<List<FileResponse>> = MutableLiveData()
    var apps: MutableLiveData<List<AppsResponse>> = MutableLiveData()


    fun getFiles(folderPath: String) {
        val fileList = FUtils.getFilesReponseList(folderPath)

        //fileList.forEach { Log.d("this",it.name) }
        files.value = fileList
    }

    fun showPopup(context: Context?, v: View) {
        context?.let {
            val popup = PopupMenu(context, v)

            // val inflater = popup.getMenuInflater()
            // inflater.inflate(R.menu.actions, popup.getMenu())
            popup.inflate(R.menu.actions)
            popup.show()
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
        val tt = App.instance.mySharedMessageBroadcastReceiver
        tt.addd(this)


        application2.registerReceiver(tt, filter)
        application2.startService(intent)


    }

    fun stopService(intent: Intent) {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_SHARE)
        val tt = App.instance.mySharedMessageBroadcastReceiver
        tt.addd(this)


        application2.registerReceiver(tt, filter)
        application2.stopService(intent)

    }


    companion object {
        var events = arrayListOf<Event>()


    }
}
