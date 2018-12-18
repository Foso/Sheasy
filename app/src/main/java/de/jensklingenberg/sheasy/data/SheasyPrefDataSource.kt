package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.model.Device

interface SheasyPrefDataSource {

    val APIV1: String
        get() = "/api/v1/"

    val defaultPath: String
        get() = "/storage/emulated/0/"

    val port: Int
    val webSocketPort: Int
    val authorizedDevices: MutableList<Device>


    fun addAuthorizedDevice(device: Device)
    fun removeDevice(device: Device)
}