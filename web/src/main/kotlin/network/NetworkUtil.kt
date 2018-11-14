package network

import data.apiVersion
import data.hostname

class NetworkUtil {
    companion object {
        val baseUrl = "http://$hostname/api/$apiVersion/"
        val webSocketbaseUrl = "ws://$hostname/api/$apiVersion/"

        val getApps = baseUrl + "apps"
        fun appDownloadUrl(packageName: String) = baseUrl + "file?apk=" + packageName
    }
}