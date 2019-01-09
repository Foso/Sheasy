package de.jensklingenberg.sheasy.data.file

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import io.reactivex.Single
import java.io.File
import java.io.InputStream


interface FileDataSource {
    fun getFiles(folderPath: String): Single<List<FileResponse>>
    fun getAssetFile(filePath: String): Single<InputStream>
    fun getApps(packageName:String=""): Single<List<AppInfo>>
    fun extractApk(appInfo: AppInfo):Boolean
    fun getTempFile(appInfo: AppInfo): File

}
