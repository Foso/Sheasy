package de.jensklingenberg.sheasy.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


class PermissionUtils {
    companion object {
        val MY_PERMISSIONS_REQUEST_CODE = 11
    }

    fun requestPermission(fragment: Fragment, requestCode: Int) {


        if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            fragment.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)


        } else {
            fragment.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
        }
    }


    fun requestPermission(fragment: Activity, requestCode: Int) {


        if (ActivityCompat.shouldShowRequestPermissionRationale(fragment,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(fragment,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)


        } else {
            ActivityCompat.requestPermissions(fragment,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
        }
    }


}