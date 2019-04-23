package de.jensklingenberg.sheasy.web.data

import network.SharedNetworkSettings
import kotlin.browser.window


class NetworkPreferences{
    /**
    Change the testingAddress to the IP of your server
     */

    val testingAddress = "192.168.178.20"

    val apiVersion = "v1"


    val port = window.location.port


    var hostUri: String = "${window}+${window.location}+${window.location.hostname}"

    val hostname = if (hostUri.contains("localhost")) {
        testingAddress
    } else {
        window.location.hostname
    }

    val baseurl = "http://$hostname:${SharedNetworkSettings.httpPort}/api/${apiVersion}/"



}