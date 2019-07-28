package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.model.FileResponse
import io.reactivex.subjects.BehaviorSubject


interface SheasyPrefDataSource {
    var appFolder: String
    val defaultPath: String
    val sharedFolders: ArrayList<FileResponse>
    val httpPort: String
    var acceptAllConnections: Boolean
    val webSocketPort: Int
    val nonInterceptedFolders: List<String>

    fun addShareFolder(folder: FileResponse)
    fun observeSharedFolders(): BehaviorSubject<List<FileResponse>>

    fun removeShareFolder(folder: FileResponse)
    fun removeAllSharedFolder()
    fun getBaseUrl(): String
}