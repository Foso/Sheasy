package de.jensklingenberg.sheasy.data


enum class MyPermission {
    READ_FILES
}

class SheasyPreferences() {

    val APIV1 = "/api/v1/"


    val port = 8766


    val activePermission = hashMapOf(MyPermission.READ_FILES to false)

    val authorizedDevices = mutableListOf<String>()


    fun checkPermission(myPermission: MyPermission): Boolean {
        return activePermission.get(myPermission) ?: false
    }

    fun changePermission(myPermission: MyPermission, allowed: Boolean) {
        activePermission[myPermission] = allowed
    }

    fun addAuthorizedDevice(ip: String) {
        authorizedDevices.add(ip)
    }
}