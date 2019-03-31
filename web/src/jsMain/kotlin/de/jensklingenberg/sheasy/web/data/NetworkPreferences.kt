package de.jensklingenberg.sheasy.web.data

import kotlin.browser.window

const val apiVersion = "v1"

class NetworkPreferences{
    /**
    Change the testingAddress to the IP of your server
     */

    val testingAddress = "192.168.178.20"

    val apiVersion = "v1"

    val httpPort = "8766"

    val websocketPort = "8765"




    var hostUri: String = "${window}+${window.location}+${window.location.hostname}"

    val hostname = if (hostUri.contains("localhost")) {
        testingAddress
    } else {
        window.location.hostname
    } +":"+httpPort

    val baseurl = "http://${hostname}/api/${apiVersion}/"



}