package de.jensklingenberg.sheasy.data.file

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.AppInfo
import io.reactivex.Maybe
import io.reactivex.Single
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

    override fun returnAssetFile(filePath: String): Single<InputStream> {
        return Single.create<InputStream> { singleEmitter ->
            singleEmitter.onSuccess(context.assets.open(filePath))
        }
    }

    override fun getFiles(folderPath: String): List<FileResponse> {
        return File(folderPath)
            .listFiles()
            ?.map {
                FileResponse(
                    it.name,
                    it.path
                )
            } ?: emptyList()

    }


    override fun getApps(): Single<List<AppInfo>> {
        return Single.create<List<AppInfo>> { singleEmitter ->
            val appsList = getAllInstalledApplications()
                .map {
                    val name = pm.getApplicationLabel(it).toString()
                    val installTime =
                        pm.getPackageInfo(it.packageName, 0).firstInstallTime.toString()
                    val packageName = it.packageName
                    val icon = pm.getApplicationIcon(it.packageName)
                    val source = it.sourceDir
                    AppInfo(
                        sourceDir = source,
                        icon = icon,
                        packageName = packageName,
                        installTime = installTime,
                        name = name
                    )
                }
            singleEmitter.onSuccess(appsList)
        }

    }

    fun getAllInstalledApplications(): List<ApplicationInfo> {
        return pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED)
            .filter { pm.getLaunchIntentForPackage(it.packageName) != null }
    }

    override fun getApplicationInfo(apkPackageName: String): Maybe<AppInfo> {
        return getApps().toObservable().flatMapIterable { x -> x }
            .filter { it.packageName == apkPackageName }.firstElement()



    }


}