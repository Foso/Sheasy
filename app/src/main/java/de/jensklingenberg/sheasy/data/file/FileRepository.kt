package de.jensklingenberg.sheasy.data.file

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import io.reactivex.Maybe
import io.reactivex.Single
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

/**
 * Created by jens on 25/2/18.
 */

class FileRepository : FileDataSource {

    val cachedApps = mutableListOf<AppInfo>()

    @Inject
    lateinit var pm: PackageManager

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var asset:AssetManager

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getAssetFile(filePath: String): Single<InputStream> {
        return Single.create<InputStream> { singleEmitter ->
            singleEmitter.onSuccess(asset.open(filePath))
        }
    }

    override fun getFiles(folderPath: String): Single<List<FileResponse>> {
        return Single.create<List<FileResponse>> { singleEmitter ->

            val files = File(folderPath)
                .listFiles()
                ?.map {
                    FileResponse(
                        it.name,
                        it.path
                    )
                } ?: emptyList()
            singleEmitter.onSuccess(files)
        }

    }


     override fun getApps(): Single<List<AppInfo>> {


        return Single.create<List<AppInfo>> { singleEmitter ->

            if (cachedApps.isNotEmpty()) {
                singleEmitter.onSuccess(cachedApps)
            }

            val appsList = getAllInstalledApplications()
                .map {

                    AppInfo(
                        sourceDir = it.sourceDir,
                        name = pm.getApplicationLabel(it).toString(),
                        packageName = it.packageName,
                        installTime = pm.getPackageInfo(
                            it.packageName,
                            0
                        ).firstInstallTime.toString()
                    )
                }
            if (appsList != cachedApps) {
                cachedApps.clear()
                cachedApps.addAll(appsList)
                singleEmitter.onSuccess(cachedApps)

            }
        }

    }



    private fun getAllInstalledApplications(): List<ApplicationInfo> {
        return pm
            .getInstalledApplications(PackageManager.PERMISSION_GRANTED)
            .filter { pm.getLaunchIntentForPackage(it.packageName) != null }
    }

    override fun getApplicationInfo(apkPackageName: String): Maybe<AppInfo> {
        return getApps()
            .toObservable()
            .flatMapIterable { x -> x }
            .filter { it.packageName == apkPackageName }
            .firstElement()


    }

    override fun extractApk(appInfo: AppInfo): Boolean {
        val file = File(appInfo.sourceDir)
        val file2 = File(sheasyPrefDataSource.appFolder + appInfo.packageName + ".apk")
        return try {
            file.copyTo(file2, true)
            true
        } catch (ioException: IOException) {
            false
        }
    }


    override fun getTempFile(appInfo: AppInfo): File {
        val file = File(appInfo.sourceDir)
        val file2 = File(sheasyPrefDataSource.appFolder +"/temp/"+ appInfo.packageName + ".apk")

            file.copyTo(file2, true)
            return file2


    }


}