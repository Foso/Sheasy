package de.jensklingenberg.sheasy.data

import android.content.SharedPreferences


enum class MyPermission {
    READ_FILES
}

class SheasyPreferences(sharedPreferences: SharedPreferences) {

    val port = sharedPreferences.getInt("PORT", 8766)


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