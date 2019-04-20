package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import kodando.rxjs.Observable
import org.w3c.files.File


interface API {

    companion object {
        private val repoSite = "https://github.com/Foso/Sheasy"
        private val baseUrl = NetworkPreferences().baseurl

        fun getRepoSite() = repoSite
        fun getAppsUrl() =                        baseUrl + "file/apps"
        fun appDownloadUrl(packageName: String) = baseUrl + "file/app?package=" + packageName
        fun fileDownloadUrl(path: String) =       baseUrl + "file/file?path=" + path
        fun getSharedFolders() =                  baseUrl + "file/shared"
        fun getFilesUrl(path: String) =           baseUrl + "file/folder?path=" + path
        fun postUploadUrl(folderPath: String) =   baseUrl + "file/shared?upload=" + folderPath

        val webSocketBaseUrl = "ws://${NetworkPreferences().hostname}:8765/"
        val notificationWebSocketURL = "${webSocketBaseUrl}notification"
        val shareWebSocketURL = "${webSocketBaseUrl}share"
        val screenshareWebSocketURL = "${webSocketBaseUrl}screenshare"

    }

    fun getApps(): Observable<List<App>>
    fun getFiles(folderPath: String) :Observable<List<FileResponse>>
    fun uploadFile(file: File,folderPath: String):Observable<Resource<State>>
    fun getShared() : Observable<List<FileResponse>>
    fun downloadApk(packageName:String)
    fun downloadFile(path:String)

}
