package de.jensklingenberg.sheasy.data

interface SheasyPrefDataSource {

    val APIV1: String
        get() = "/api/v1/"

    val defaultPath: String
        get() = "/storage/emulated/0/"

    val port: Int
    val webSocketPort: Int
    val authorizedDevices: MutableList<String>


    fun addAuthorizedDevice(ip: String)
}