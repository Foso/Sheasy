package de.jensklingenberg.sheasy.data

import android.graphics.drawable.Drawable
import model.AppFile
import model.FileResponse
import java.io.InputStream


data class AppInfo(val sourceDir: String, val icon: Drawable)
interface FileDataSource {
    fun getFiles(folderPath: String): List<FileResponse>
    fun returnAssetFile(filePath: String): InputStream
    fun getApps(): List<AppFile>
    fun getApplicationInfo(apkPackageName: String): AppInfo
}
