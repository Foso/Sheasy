package model


data class ConnectionInfo(val result: String, val deviceName: String, val files: List<FileResponse>)
data class DownloadRequest(val url: String, val dest: String)

data class IntentRequest(val action: String)


data class DeviceResponse(
    val manufacturer: String,
    val model: String,
    val busySpace: Int,
    val totalSpace: Int,
    val freeSpace: Int,
    val androidVersion: String
)

data class AppFile(val name: String, val packageName: String, val installTime: String)
data class ServiceResponse(val name: String)
data class FileResponse(val name: String, val path: String)
data class CommandResponse(val name: String)
data class ContactResponse(val name: String, val phone: String)
