package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.web.model.Device


interface SheasyPrefDataSource {

    var appFolder: String

    val defaultPath: String
    val sharedFolders:List<String>


    val httpPort: Int
    var acceptAllConnections: Boolean
    val webSocketPort: Int
    val authorizedDevices: MutableList<Device>


    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)
}