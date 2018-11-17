package model



data class DeviceResponse(
    val manufacturer: String,
    val model: String,
    val busySpace: Int,
    val totalSpace: Int,
    val freeSpace: Int,
    val androidVersion: String
)

data class AppFile(val name: String, val packageName: String, val installTime: String)
data class FileResponse(val name: String, val path: String)
