package de.jensklingenberg.sheasy.data

import android.content.pm.ApplicationInfo
import model.AppFile
import model.FileResponse
import java.io.InputStream

interface FileDataSource {
    fun getFiles(folderPath: String): List<FileResponse>
    fun returnAssetFile(filePath: String): InputStream
    fun getApps(): List<AppFile>
    fun getApplicationInfo(apkPackageName: String): ApplicationInfo?
}
