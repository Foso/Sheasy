package de.jensklingenberg.sheasy.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


class PermissionUtils {
    companion object {
        val MY_PERMISSIONS_REQUEST_CODE = 11
    }

    fun requestPermission(fragment:Fragment,requestCode:Int) {
        if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            fragment.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)


        } else {
            fragment.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        }
    }



    fun checkPermStorage(
        activity: FragmentActivity,
        permissionName: String
    ) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permissionName
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permissionName
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permissionName),
                    MY_PERMISSIONS_REQUEST_CODE
                )
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permissionName),
                    MY_PERMISSIONS_REQUEST_CODE
                )

                // MY_PERMISSIONS_REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}