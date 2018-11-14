package de.jensklingenberg.sheasy.legacy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Environment
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppsResponse
import model.AppFile
import java.io.File
import javax.inject.Inject

/**
 * Created by jens on 18/2/18.
 */
class AppUtils {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var packageManager: PackageManager


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.oldAppComponent.inject(this)

    fun getAppsResponseList2(): List<AppFile> {

        val pm1 = packageManager
        return getAllInstalledApplications()
            .map {
                val name = pm1.getApplicationLabel(it).toString()
                val installTime = pm1.getPackageInfo(it.packageName, 0).firstInstallTime.toString()
                AppFile(name, it.packageName, installTime)
            }.toList()
    }

    fun getAllInstalledApplications(): List<ApplicationInfo> {
        val pm = packageManager

        return pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED).filter {
            pm.getLaunchIntentForPackage(it.packageName) != null
        }.toList<ApplicationInfo>()
    }

    fun returnAPK(apkPackageName: String): ApplicationInfo? {
        return getAllInstalledApplications().first { it.packageName == apkPackageName }
    }

    fun getAppsResponseList(): List<AppsResponse> {
        val pm1 = context.packageManager
        val appResponseList = getAllInstalledApplications().map {
            val name = pm1.getApplicationLabel(it).toString()
            val icon = pm1.getApplicationIcon(it)

            val installTime = pm1.getPackageInfo(it.packageName, 0).firstInstallTime.toString()
            AppsResponse(name, it.packageName, installTime, icon)
        }.toList()

        return appResponseList
    }

    fun extractApk(packageName: String): Boolean {

        val path =
            Environment.getExternalStorageDirectory().toString() + "/" + context.getString(
                R.string.app_folder_directory
            ) + "/"

        getAllInstalledApplications().filter {
            it.packageName == packageName
        }
            .forEach {

                val file = File(it.sourceDir)
                val file2 = File(path + it.packageName + ".apk")
                file.copyTo(file2)
                return true


            }

        return false
    }

}

