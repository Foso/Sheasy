package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.data.NetworkPreferences

//@formatter:off
class ApiEndPoint {

    companion object {
        private val repoSite = "https://github.com/Foso/Sheasy"
        private val baseUrl = NetworkPreferences().baseurl

        fun getRepoSite() = repoSite
        fun getAppsUrl() =                        baseUrl + "file/apps"
        fun appDownloadUrl(packageName: String) = baseUrl + "file/app?package=" + packageName
        fun fileDownloadUrl(path: String) =       baseUrl + "file/file?path=" + path
        fun getSharedFolders() =                  baseUrl + "file/shared"
        fun getFilesUrl(path: String) =           baseUrl + "file/shared?folder=" + path

        fun postUploadUrl(folderPath: String) =   baseUrl + "file/shared?upload=" + folderPath

        val notificationWebSocketURL = "ws://${NetworkPreferences().hostname}:8765/notification"
        val shareWebSocketURL = "ws://${NetworkPreferences().hostname}:8765/share"
        val screenshareWebSocketURL = "ws://${NetworkPreferences().hostname}:8765/screenshare"

    }
}