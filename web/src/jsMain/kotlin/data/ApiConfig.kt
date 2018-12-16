package data

import kotlin.browser.window

const val apiVersion = "v1"

var tt: String = "${window}+${window.location}+${window.location.hostname}"

var hostname = if (tt.contains("localhost")) {
    "192.168.178.20:8766"
} else {
    tt + ":8766"

}
