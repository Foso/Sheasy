package de.jensklingenberg.sheasy.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import de.jensklingenberg.sheasy.App
import model.AppFile
import model.FileResponse
import java.io.File
import java.io.InputStream
import javax.inject.Inject

/**
 * Created by jens on 25/2/18.
 */

open class FileRepository : FileDataSource {


    @Inject
    lateinit var pm: PackageManager

    @Inject
    lateinit var context: Context

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun returnAssetFile(filePath: String): InputStream = context.assets.open(filePath)

    override fun getFiles(folderPath: String): List<FileResponse> {
        return File(folderPath).listFiles()?.map {
            FileResponse(
                it.name,
                it.path
            )
        } ?: emptyList()

    }


    override fun getApps(): List<AppFile> {
        return getAllInstalledApplications()
            .map {
                val name = pm.getApplicationLabel(it).toString()
                val installTime = pm.getPackageInfo(it.packageName, 0).firstInstallTime.toString()
                val packageName= it.packageName
                AppFile(name, packageName, installTime)
            }
    }

    fun getAllInstalledApplications(): List<ApplicationInfo> {
        return pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED).filter {
            pm.getLaunchIntentForPackage(it.packageName) != null
        }
    }

    override fun getApplicationInfo(apkPackageName: String): AppInfo {
        return getAllInstalledApplications().first { it.packageName == apkPackageName }.run {
            val icon = pm.getApplicationIcon(this)
            return@run AppInfo(this.sourceDir, icon)
        }
    }


}