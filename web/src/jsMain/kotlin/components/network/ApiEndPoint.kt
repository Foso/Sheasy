package components.network

import data.apiVersion
import data.hostname

class ApiEndPoint {

    companion object {
        val apps = "apps"
        val repoSite = "https://github.com/Foso/Sheasy"



        val baseUrl = "http://$hostname/api/$apiVersion/"
        val webSocketbaseUrl = "ws://$hostname/api/$apiVersion/"

        fun getFiles(path: String) = baseUrl + "file?shared=" + path
        fun appDownloadUrl(packageName: String) = baseUrl + "file?apk=" + packageName
        fun appIconUrl(packageName: String) = baseUrl + "file?icon=" + packageName
        val notificationWebSocketURL = "ws://192.168.178.20:8765/echo"
        val screenshareWebSocketURL = "ws://192.168.178.20:8765/screenshare"

    }
}