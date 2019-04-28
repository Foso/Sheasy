package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.InputStream


interface FileDataSource {

    /**
     *
     */
    fun observeFiles(folderPath: String): Single<List<File>>


    /**
     *
     */
    fun getFile(filePath: String, isAssetFile: Boolean = false): Single<File>

    /**
     * Get a list of the user installed Apps
     *
     * @param packageName the packageName of the Apk
     * @return A Single with a List of [AppInfo]
     */
    fun getApps(packageName: String = ""): Single<List<AppInfo>>

    /**
     *
     */
    fun extractApk(appInfo: AppInfo): Completable

    /**
     *
     */
    fun createTempFile(appInfo: AppInfo): File

    /**
     *
     */
    fun saveUploadedFile(destinationFilePath: String, inputStream: InputStream): Completable


}
