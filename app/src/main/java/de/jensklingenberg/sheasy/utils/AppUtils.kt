package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Environment
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppsResponse
import java.io.File

/**
 * Created by jens on 18/2/18.
 */
class AppUtils {


    companion object {

        fun getAppsResponseList(context: Context): List<AppsResponse> {
            val pm = context.packageManager

            val appResponseList = getAllInstalledApplications(context).map {
                val name = pm.getApplicationLabel(it).toString()
                val installTime = pm.getPackageInfo(it.packageName, 0).firstInstallTime.toString()
                AppsResponse(name, it.packageName, installTime)
            }.toList()

            return appResponseList
        }

        fun getAllInstalledApplications(context: Context): List<ApplicationInfo> {
            val pm = context.packageManager

            val installedApps =
                pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED).filter {
                    pm.getLaunchIntentForPackage(it.packageName) != null
                }.toList()

            return installedApps
        }

        fun extractApk(context: Context, packageName: String): Boolean {

            val path =
                Environment.getExternalStorageDirectory().toString() + "/" + context.getString(
                    R.string.app_folder_directory
                ) + "/"

            AppUtils.getAllInstalledApplications(context).forEach {
                if (it.packageName == packageName) {
                    val file = File(it.sourceDir)
                    val file2 = File(path + it.packageName + ".apk")
                    file.copyTo(file2, true)
                    return true
                }

            }

            return false
        }
    }
}

