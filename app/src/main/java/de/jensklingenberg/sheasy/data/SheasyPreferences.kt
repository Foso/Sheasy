package de.jensklingenberg.sheasy.data


class SheasyPreferences : SheasyPrefDataSource {
    override val webSocketPort = 8765


    override val APIV1 = "/api/v1/"

    override val defaultPath = "/storage/emulated/0/"
    override val port = 8766

    override val authorizedDevices = mutableListOf<String>()

    override fun addAuthorizedDevice(ip: String) {
        authorizedDevices.add(ip)
    }
}