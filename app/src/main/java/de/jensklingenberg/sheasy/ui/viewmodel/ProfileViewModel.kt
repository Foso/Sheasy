package de.jensklingenberg.sheasy.ui.viewmodel;

import android.Manifest
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.*
import android.content.pm.PackageManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.interfaces.ApiEventListener
import de.jensklingenberg.sheasy.network.MyHttpServerImpl
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.model.Resource
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.utils.PermissionUtils.Companion.MY_PERMISSIONS_REQUEST_READ_CONTACTS
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


class ProfileViewModel(val application2: Application) : AndroidViewModel(application2), ApiEventListener {

    var shareMessage: MutableLiveData<ArrayList<Event>> = MutableLiveData()
    var storagePermission: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var notificationPermissionStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var contactsPermissionStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var sharedFolder: MutableLiveData<String> = MutableLiveData()


    override fun onShare(test: Event) {
        events.add(test)
        shareMessage.value = events
    }


    fun setSharedFolder(string:String){
        sharedFolder.value=string
    }

    fun startService( intent: Intent) {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_SHARE)
        val tt = App.instance.mySharedMessageBroadcastReceiver
        tt.addd(this)


       application2.registerReceiver(tt, filter);
        application2.startService(intent)

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



    }
}
