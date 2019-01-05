package repository

import de.jensklingenberg.sheasy.web.model.Device


interface SheasyPrefDataSource {

    val APIV1: String
        get() = "/api/v1/"
    var appFolder: String

    val defaultPath: String

    val httpPort: Int
    var acceptAllConnections: Boolean
    val webSocketPort: Int
    val authorizedDevices: MutableList<Device>


    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)
}