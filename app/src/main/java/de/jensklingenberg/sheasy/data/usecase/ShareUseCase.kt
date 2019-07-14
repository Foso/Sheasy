package de.jensklingenberg.sheasy.data.usecase

import android.content.Intent
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import java.io.File

interface ShareUseCase {
    fun share(file: File)
    fun hostFolder(fileResponse: FileResponse)
    fun removeHostFolder(fileResponse: FileResponse)
    fun shareApp(appInfo: AppInfo)
    fun shareDownloadLink(appInfo: AppInfo)
    fun shareDownloadLink(link: FileResponse)
    fun shareDownloadLink(message: String)
    fun feedbackMailIntent(): Intent
}