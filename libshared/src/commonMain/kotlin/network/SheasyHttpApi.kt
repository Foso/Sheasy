package network

interface SheasyHttpApi {
    fun getRepoSite(): String
    fun getAppsUrl(): String
    fun appDownloadUrl(packageName: String):String
    fun getFilesUrl(path: String) : String
    fun getSharedFoldersUrl() : String
    fun postUploadUrl(folderPath: String) : String
    fun fileDownloadUrl(path: String) :String

}