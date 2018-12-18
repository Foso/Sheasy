package network

import data.apiVersion
import data.hostname

class NetworkUtil {
    companion object {
        val baseUrl = "http://$hostname/api/$apiVersion/"
        val webSocketbaseUrl = "ws://$hostname/api/$apiVersion/"

        val getApps = baseUrl + "apps"
        fun getFiles(path:String) = baseUrl + "file?shared="+path
        fun appDownloadUrl(packageName: String) = baseUrl + "file?apk=" + packageName
        fun appIconUrl(packageName: String) = baseUrl + "file?icon=" + packageName
        val repoSite = "https://github.com/Foso/Sheasy"
        val notificationWebSocketURL = "ws://192.168.178.20:8765/echo"
        val screenshareWebSocketURL = "ws://192.168.178.20:8765/screenshare"

    }
}