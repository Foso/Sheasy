package de.jensklingenberg.sheasy.data.file

import io.reactivex.Maybe
import io.reactivex.Single
import de.jensklingenberg.sheasy.web.model.AppInfo
import de.jensklingenberg.sheasy.web.model.FileResponse
import java.io.File
import java.io.InputStream


interface FileDataSource {
    fun getFiles(folderPath: String): Single<List<FileResponse>>
    fun getAssetFile(filePath: String): Single<InputStream>
    fun getApps(): Single<List<AppInfo>>
    fun getApplicationInfo(apkPackageName: String): Maybe<AppInfo>
    fun extractApk(appInfo: AppInfo):Boolean
    fun getTempFile(appInfo: AppInfo): File

}
