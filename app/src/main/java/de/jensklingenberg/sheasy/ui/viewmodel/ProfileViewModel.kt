package de.jensklingenberg.sheasy.ui.viewmodel;

import android.Manifest
import android.app.Activity
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.*
import android.content.pm.PackageManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.network.ApiEventListener
import de.jensklingenberg.sheasy.network.MyHttpServer
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.Resource
import java.io.IOException
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import de.jensklingenberg.sheasy.utils.PermissionUtils.Companion.MY_PERMISSIONS_REQUEST_READ_CONTACTS


class ProfileViewModel(application: Application) : AndroidViewModel(application), ApiEventListener {

    var shareMessage: MutableLiveData<ArrayList<Event>> = MutableLiveData()
    var storagePermission: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var notificationPermissionStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var contactsPermissionStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()


    override fun onShare(test: Event) {
        events.add(test)
        shareMessage.value = events
    }

    fun startService(activity: MainActivity, intent: Intent) {
        val filter = IntentFilter(MyHttpServer.ACTION_SHARE)
        val tt = MyBroadcastReceiver(this)
        activity.registerReceiver(tt, filter);
        activity.startService(intent)

    }

    fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            storagePermission.value = Resource.success(true)
        }
        else  {
            storagePermission.value = Resource.success(false)
        }
    }

    fun checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            contactsPermissionStatus.value = Resource.success(true)
        }
        else  {
            contactsPermissionStatus.value = Resource.success(false)
        }
    }

    fun checkNotifcationPermission(context: Context){
        var weHaveNotificationListenerPermission = false
        for (service in NotificationManagerCompat.getEnabledListenerPackages(context)) {
            if (service == context.packageName)
                weHaveNotificationListenerPermission = true
        }
        if(weHaveNotificationListenerPermission){
            notificationPermissionStatus.value = Resource.success(true)
        }else{
            notificationPermissionStatus.value = Resource.success(false)
        }
    }

    fun requestContactsPermission(context: AppCompatActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                        Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(context,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        }

    }

     fun requestNotificationPermission(context: Context) {
        ContextCompat.startActivity(context, Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS), null)
    }

    fun disableNotificationPermission(){
        notificationPermissionStatus.value = Resource.success(false)

    }

    companion object {
        var events = arrayListOf<Event>()

        class MyBroadcastReceiver(val apiEventListener: ApiEventListener) : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val test: Event = intent.extras.getParcelable(MyHttpServer.ACTION_SHARE)

                try {
                    apiEventListener.onShare(test)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }


        }

    }
}
