package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.data.DevicesDataSource
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


interface SheasyPrefDataSource {
    var appFolder: String
    val defaultPath: String
    val sharedFolders: ArrayList<FileResponse>
    val httpPort: Int
    var acceptAllConnections: Boolean
    val webSocketPort: Int
    val devicesRepository: DevicesDataSource
    val nonInterceptedFolders: List<String>

    fun addShareFolder(folder:FileResponse)
    fun observeSharedFolders() : BehaviorSubject<List<FileResponse>>

    fun removeShareFolder(folder: FileResponse)
}