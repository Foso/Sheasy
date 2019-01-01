package de.jensklingenberg.sheasy.web.data

import kotlin.browser.window

const val apiVersion = "v1"

class NetworkPreferences{
    /**
    Change the testingAddress to the IP of your server
     */

    val testingAddress = "192.168.178.81:8766"
    companion object {
        const val apiVersion = "v1"


    }


    var hostUri: String = "${window}+${window.location}+${window.location.hostname}"

    val hostname = if (hostUri.contains("localhost")) {
        testingAddress
    } else {
        window.location.hostname + ":8766"
    }




}