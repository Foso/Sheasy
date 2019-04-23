package network

class SharedNetworkSettings(val baseUrl: String) : SharedNetworkSettingsSource {


    override fun fileDownloadUrl(path: String): String {
        return baseUrl + "file/file?path=" + path
    }


    override fun postUploadUrl(folderPath: String): String {
        return baseUrl + "file/shared?upload=" + folderPath
    }

    override fun getSharedFoldersUrl(): String {
        return baseUrl + "file/shared"
    }

    override fun getFilesUrl(path: String): String {
        return baseUrl + "file/folder?path=" + path

    }

    override fun appDownloadUrl(packageName: String):String {
       return baseUrl + "file/app?package=" + packageName
    }


    override fun getAppsUrl(): String {
        return baseUrl + "file/apps"
    }

    override fun getRepoSite(): String {
        return "https://github.com/Foso/Sheasy"
    }


    companion object {
        val httpPort = "8766"
        val websocketPort = "8765"



    }


}