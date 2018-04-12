package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.AppsResponse

/**
 * Created by jens on 18/2/18.
 */
class AppUtils {

    companion object {

        fun handleApps(context: Context): List<AppsResponse> {
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

            val installedApps = pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED).filter {
                pm.getLaunchIntentForPackage(it.packageName) != null
            }.toList()

            return installedApps
        }
    }
}

