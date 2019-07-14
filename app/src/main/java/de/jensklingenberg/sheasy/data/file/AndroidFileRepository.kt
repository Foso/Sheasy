package de.jensklingenberg.sheasy.data.file

import android.content.pm.PackageManager
import android.content.res.AssetManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AndroidAppInfo
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.Completable
import io.reactivex.Single
import java.io.*
import javax.inject.Inject


open class AndroidFileRepository : FileDataSource {


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


    override fun getFile(filePath: String, isAssetFile: Boolean): Single<File> = Single.create<File> { singleEmitter ->
        try {
            if (isAssetFile) {
                val tempFile = File.createTempFile("prefix-", "-suffix")

                singleEmitter.onSuccess(writeBytesToFile(assetManager.open(filePath), tempFile))
            } else {
                singleEmitter.onSuccess(File(filePath))
            }

        } catch (ioEx: IOException) {
            singleEmitter.onError(ioEx)
        }

    }

    @Throws(IOException::class)
    private fun writeBytesToFile(input: InputStream, file: File): File {
        var fos: FileOutputStream? = null
        try {
            val data = ByteArray(2048)
            fos = FileOutputStream(file)
            var test = input.read(data)
            while ((test) > -1) {

                fos.write(data, 0, test)
                test = input.read(data)
            }
            return file
        } catch (ex: Exception) {
            //logger.error("Exception", ex)
        } finally {
            fos?.close()
            return file
        }
    }


    override fun observeFiles(folderPath: String): Single<List<File>> = Single.create<List<File>> { singleEmitter ->
        try {
            File(folderPath)
                .listFiles()
                .sortedBy { it.name }
                .run {
                    singleEmitter.onSuccess(this)
                }
        } catch (ioException: IOException) {
            singleEmitter.onError(ioException)
        }
    }


    override fun getApps(packageName: String): Single<List<AppInfo>> = Single.create<List<AppInfo>> { singleEmitter ->

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


    override fun extractApk(appInfo: AppInfo): Completable {
        return Completable.create { completableEmitter ->
            val file = File(appInfo.sourceDir)
            val file2 = File(sheasyPrefDataSource.appFolder + "/temp/" + appInfo.packageName + ".apk")
            try {
                file.copyTo(file2, true)
                completableEmitter.onComplete()
            } catch (ioException: IOException) {
                completableEmitter.onError(ioException)
            }

        }


    }

    override fun createTempFile(appInfo: AppInfo): File {
        val file = File(appInfo.sourceDir)
        val file2 = File(sheasyPrefDataSource.appFolder + "/temp/" + appInfo.packageName + ".apk")

        file.copyTo(file2, true)
        return file
    }

    override fun saveUploadedFile(destinationFilePath: String, inputStream: InputStream): Completable =
        Completable.create { completableEmitter ->
            try {
                val destinationFile = File(destinationFilePath)
                inputStream.copyTo(destinationFile.outputStream())
                inputStream.close()
                if (File(destinationFilePath).exists()) {
                    completableEmitter.onComplete()
                } else {
                    completableEmitter.onError(SheasyError.UploadFailedError())
                }
            } catch (io: FileNotFoundException) {
                completableEmitter.onError(io)
            }

        }

}