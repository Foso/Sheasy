package de.jensklingenberg.sheasy.data.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.utils.PermissionUtils.Companion.MY_PERMISSIONS_REQUEST_READ_CONTACTS
import de.jensklingenberg.sheasy.utils.extension.ResourceMediatorLiveData
import javax.inject.Inject


class PermissionViewModel @Inject constructor(val application2: Application) :
    BaseViewModel(application2) {

    var storagePermission: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var notificationPermissionStatus: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    var contactsPermissionStatus: ResourceMediatorLiveData<Resource<Boolean>> =
        ResourceMediatorLiveData()

    fun checkStoragePermission() {
        val permGranted = checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        storagePermission.value = Resource.success(permGranted)
    }

    fun checkPermission(permissionType: String): Boolean {
        return (ContextCompat.checkSelfPermission(
            getApplication(),
            permissionType
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun checkContactsPermission() {
        val permGranted = checkPermission(Manifest.permission.READ_CONTACTS)
        contactsPermissionStatus.value = Resource.success(permGranted)
    }

    fun checkNotifcationPermission(context: Context) {

        var weHaveNotificationListenerPermission = false
        for (service in NotificationManagerCompat.getEnabledListenerPackages(context)) {
            if (service == context.packageName)
                weHaveNotificationListenerPermission = true
        }
        notificationPermissionStatus.value = Resource.success(weHaveNotificationListenerPermission)

    }

    fun requestNotificationPermission(context: Context) {
        ContextCompat.startActivity(
            context,
            Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS),
            null
        )
    }

    fun disableNotificationPermission() {
        notificationPermissionStatus.value = Resource.success(false)
    }

    fun requestStorage(context: Activity) {

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS
                )
                storagePermission.value = Resource.success(true)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            storagePermission.value = Resource.success(true)

        }
    }

    fun requestContactsPermission(context: AppCompatActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                context,
                Manifest.permission.READ_CONTACTS
            )
        ) {
        } else {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.READ_CONTACTS),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS
            )
        }


    }


}
