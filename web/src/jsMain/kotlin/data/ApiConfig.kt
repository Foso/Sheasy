package data

import kotlin.browser.window

const val apiVersion = "v1"
/**
Change the testingAddress to the IP of your server
 */
const val testingAddress =  "192.168.178.20:8766"

var hostUri: String = "${window}+${window.location}+${window.location.hostname}"

var hostname = if (hostUri.contains("localhost")) {
    testingAddress
} else {
    window.location.hostname + ":8766"
}
