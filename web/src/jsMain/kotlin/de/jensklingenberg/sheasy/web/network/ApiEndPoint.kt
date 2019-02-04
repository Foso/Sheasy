package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.apiVersion

class ApiEndPoint {

    companion object {
        val apps = "file/apps"
        val shared="file/shared"
        val repoSite = "https://github.com/Foso/Sheasy"
        val upload = "file/upload"


        val baseUrl = "http://${NetworkPreferences().hostname}/api/$apiVersion/"
        val webSocketbaseUrl = "ws://${NetworkPreferences().hostname}/api/$apiVersion/"

        fun getFiles(path: String) = baseUrl + "file/shared?folder=" + path
        fun appDownloadUrl(packageName: String) = baseUrl + "file/app?package=" + packageName
        fun fileDownloadUrl(path: String) = baseUrl + "file/file?path=" + path

        fun appIconUrl(packageName: String) = baseUrl + "file?icon=" + packageName
        val notificationWebSocketURL = "ws://192.168.178.20:8765/echo"
        val screenshareWebSocketURL = "ws://192.168.178.20:8765/screenshare"

    }
}