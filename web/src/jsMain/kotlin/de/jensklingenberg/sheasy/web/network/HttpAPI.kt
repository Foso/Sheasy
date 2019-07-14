package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import kodando.rxjs.Observable
import network.SheasyHttpApi
import org.w3c.files.File


interface HttpAPI : SheasyHttpApi {

    companion object {

        val webSocketBaseUrl = "ws://${NetworkPreferences().hostname}:8766/"
        val notificationWebSocketURL = "${webSocketBaseUrl}notification"
        val shareWebSocketURL = "${webSocketBaseUrl}share"
        val screenshareWebSocketURL = "${webSocketBaseUrl}screenshare"

    }

    fun getApps(): Observable<List<App>>
    fun getFiles(folderPath: String): Observable<List<FileResponse>>
    fun uploadFile(file: File, folderPath: String): Observable<Resource<State>>
    fun getShared(): Observable<List<FileResponse>>
    fun downloadApk(packageName: String)
    fun downloadFile(path: String)

}
