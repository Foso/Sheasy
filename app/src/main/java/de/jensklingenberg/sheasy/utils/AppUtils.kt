package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Environment
import de.jensklingenberg.sheasy.R

import java.io.File

/**
 * Created by jens on 18/2/18.
 */
class AppUtils(val context: Context) {



    fun extractApk( packageName: String): Boolean {

        val path =
            Environment.getExternalStorageDirectory().toString() + "/" + context.getString(
                R.string.app_folder_directory
            ) + "/"

        val pm = context.packageManager
        pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED).filter {
            pm.getLaunchIntentForPackage(it.packageName) != null
        }.toList<ApplicationInfo>().forEach {
            if (it.packageName == packageName) {
                val file = File(it.sourceDir)
                val file2 = File(path + it.packageName + ".apk")
                file.copyTo(file2)
                return true
            }

        }

        return false
    }

}

