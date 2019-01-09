package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.devices.DevicesDataSource
import de.jensklingenberg.sheasy.web.model.Device


interface SheasyPrefDataSource {

    var appFolder: String

    val defaultPath: String
    val sharedFolders:List<FileResponse>


    val httpPort: Int
    var acceptAllConnections: Boolean
    val webSocketPort: Int
    val devicesRepository: DevicesDataSource

}