package repository

import de.jensklingenberg.model.Device


interface SheasyPrefDataSource {

    val APIV1: String
        get() = "/api/v1/"

    val defaultPath: String

    val port: Int
    val acceptAllConnections:Boolean
    val webSocketPort: Int
    val authorizedDevices: MutableList<Device>


    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)
}