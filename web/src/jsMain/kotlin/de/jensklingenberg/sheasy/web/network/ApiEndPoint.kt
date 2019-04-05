package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.apiVersion

class ApiEndPoint {

    companion object {
        val apps = "file/apps"
        val shared="file/shared"
        val repoSite = "https://github.com/Foso/Sheasy"
        val upload = "file/upload"

        val baseUrl = NetworkPreferences().baseurl

        fun getFiles(path: String) = baseUrl + "file/shared?folder=" + path
        fun appDownloadUrl(packageName: String) = baseUrl + "file/app?package=" + packageName
        fun fileDownloadUrl(path: String) = baseUrl + "file/file?path=" + path

        fun appIconUrl(packageName: String) = baseUrl + "file?icon=" + packageName
        val notificationWebSocketURL = "ws://${NetworkPreferences().hostname}:8765/notification"
        val shareWebSocketURL = "ws://${NetworkPreferences().hostname}:8765/share"

        val screenshareWebSocketURL = "ws://${NetworkPreferences().hostname}:8765/screenshare"

    }
}