package de.jensklingenberg.sheasy.data.file

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.Single
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


open class FileRepository : FileDataSource {

    private val cachedApps = mutableListOf<AppInfo>()

    @Inject
    lateinit var pm: PackageManager

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var asset: AssetManager

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getAssetFile(filePath: String): Single<InputStream> {
        return Single.create<InputStream> { singleEmitter ->

            val inputStream = asset.open(filePath)
            singleEmitter.onSuccess(inputStream)
        }
    }

    override fun getFiles(folderPath: String): Single<List<FileResponse>> {
        return Single.create<List<FileResponse>> { singleEmitter ->
            try {
                File(folderPath)
                    .listFiles()
                    .map {
                        FileResponse(
                            it.name,
                            it.path
                        )
                    }
                    .sortedBy { it.name }
                    .run {
                        singleEmitter.onSuccess(this)
                    }
            } catch (ioException: IOException) {
                singleEmitter.onError(ioException)
            }
        }
    }


    override fun getApps(packageName: String): Single<List<AppInfo>> {
        return Single.create<List<AppInfo>> { singleEmitter ->

            if (cachedApps.isNotEmpty()) {
                cachedApps
                    .filter {
                        when {
                            packageName.isNotBlank() -> return@filter it.packageName == packageName
                            else -> true
                        }
                    }.run {
                        singleEmitter.onSuccess(this)
                    }
            }

            val appsList = getAllInstalledApplications()
                .map {
                    mapToAppInfo(it)
                }

            if (appsList != cachedApps) {
                cachedApps.clear()
                cachedApps.addAll(appsList)

                cachedApps
                    .filter {
                        when {
                            packageName.isNotBlank() -> return@filter it.packageName == packageName
                            else -> true
                        }
                    }.run {
                        singleEmitter.onSuccess(this)
                    }
            }
        }

    }

    private fun mapToAppInfo(it: ApplicationInfo): AppInfo {
        return AppInfo(
            sourceDir = it.sourceDir,
            name = pm.getApplicationLabel(it).toString(),
            packageName = it.packageName,
            installTime = pm.getPackageInfo(
                it.packageName,
                0
            ).firstInstallTime.toString()
        )
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
        val file2 = File(sheasyPrefDataSource.appFolder + "/temp/" + appInfo.packageName + ".apk")

        file.copyTo(file2, true)
        return file2
    }

    private fun getAllInstalledApplications(): List<ApplicationInfo> {
        return pm
            .getInstalledApplications(PackageManager.PERMISSION_GRANTED)
            .filter { pm.getLaunchIntentForPackage(it.packageName) != null }
    }


}