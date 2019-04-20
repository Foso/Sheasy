package de.jensklingenberg.sheasy.data.file

import android.content.pm.PackageManager
import android.content.res.AssetManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AndroidAppInfo
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.Completable
import io.reactivex.Single
import java.io.*
import javax.inject.Inject


open class FileRepository : FileDataSource {


    private val cachedApps = mutableListOf<AppInfo>()

    @Inject
    lateinit var packageManager: PackageManager

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var assetManager: AssetManager

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun getFile(filePath: String, isAssetFile: Boolean): Single<InputStream> {
        return Single.create<InputStream> { singleEmitter ->
            try {
                if (isAssetFile) {
                    singleEmitter.onSuccess(assetManager.open(filePath))
                } else {
                    val fileInputStream = FileInputStream(File(filePath))
                    singleEmitter.onSuccess(fileInputStream)
                }

            } catch (ioEx: IOException) {
                singleEmitter.onError(ioEx)
            }

        }
    }


    override fun observeFiles(folderPath: String): Single<List<FileResponse>> {
        return Single.create<List<FileResponse>> { singleEmitter ->
            try {
                File(folderPath)
                    .listFiles()
                    .sortedBy { it.name }
                    .map {
                        FileResponse(
                            it.name,
                            it.path
                        )
                    }
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

            val appsList = packageManager
                .getInstalledApplications(PackageManager.PERMISSION_GRANTED)
                .filter { packageManager.getLaunchIntentForPackage(it.packageName) != null }
                .map {
                    AndroidAppInfo(
                        sourceDir = it.sourceDir,
                        name = packageManager.getApplicationLabel(it).toString(),
                        packageName = it.packageName,
                        installTime = packageManager.getPackageInfo(
                            it.packageName,
                            0
                        ).firstInstallTime.toString(),
                        drawable = packageManager.getApplicationIcon(it.packageName)
                    )
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


    override fun extractApk(appInfo: AppInfo): Completable {
        return Completable.create { singleEmitter ->
            val file = File(appInfo.sourceDir)
            val file2 = File(sheasyPrefDataSource.appFolder + appInfo.packageName + ".apk")
            try {
                file.copyTo(file2, true)
                singleEmitter.onComplete()
            } catch (ioException: IOException) {
                singleEmitter.onError(ioException)
            }

        }


    }

    override fun createTempFile(appInfo: AppInfo): File {
        val file = File(appInfo.sourceDir)
        val file2 = File(sheasyPrefDataSource.appFolder + "/temp/" + appInfo.packageName + ".apk")

        file.copyTo(file2, true)
        return file2
    }

    override fun saveUploadedFile(destinationFilePath: String, inputStream: InputStream): Completable {
        return Completable.create { singleEmitter ->

            try {
                val destinationFile = File(destinationFilePath)
                inputStream.copyTo(destinationFile.outputStream())
                inputStream.close()
                if (File(destinationFilePath).exists()) {
                    singleEmitter.onComplete()
                } else {
                    singleEmitter.onError(SheasyError.UploadFailedError())
                }
            } catch (io: FileNotFoundException) {
                singleEmitter.onError(io)
            }

        }
    }

}