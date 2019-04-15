package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.devices.DevicesDataSource
import io.reactivex.Observable


interface SheasyPrefDataSource {

    var appFolder: String

    val defaultPath: String
    val sharedFolders: ArrayList<FileResponse>


    fun addShareFolder(folder:FileResponse)


    val httpPort: Int
    var acceptAllConnections: Boolean
    val webSocketPort: Int
    val devicesRepository: DevicesDataSource

    val nonInterceptedFolders: List<String>

    fun sharedFoldersObs() : Observable<List<FileResponse>>

}