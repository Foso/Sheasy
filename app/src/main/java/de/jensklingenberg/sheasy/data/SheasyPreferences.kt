package de.jensklingenberg.sheasy.data


class SheasyPreferences() {

    val APIV1 = "/api/v1/"

    val defaultPath = "/storage/emulated/0/"
    val port = 8766

    val authorizedDevices = mutableListOf<String>()

    fun addAuthorizedDevice(ip: String) {
        authorizedDevices.add(ip)
    }
}