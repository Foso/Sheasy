package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import de.jensklingenberg.sheasy.App
import model.AppFile
import javax.inject.Inject

/**
 * Created by jens on 18/2/18.
 */
class AppsRepository :IAppsRepostitoy{


    @Inject
    lateinit var pm: PackageManager


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getApps(): List<AppFile> {
        return getAllInstalledApplications()
            .map {
                val name = pm.getApplicationLabel(it).toString()
                val installTime = pm.getPackageInfo(it.packageName, 0).firstInstallTime.toString()
                AppFile(name, it.packageName, installTime)
            }
    }

    fun getAllInstalledApplications(): List<ApplicationInfo> {
        return pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED).filter {
            pm.getLaunchIntentForPackage(it.packageName) != null
        }
    }

    override fun returnAPK(apkPackageName: String): ApplicationInfo? {
        return getAllInstalledApplications().first { it.packageName == apkPackageName }
    }



}

