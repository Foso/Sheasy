package model



data class DeviceResponse(
    val manufacturer: String,
    val model: String,
    val busySpace: Int,
    val totalSpace: Int,
    val freeSpace: Int,
    val androidVersion: String
)

data class FileResponse(val name: String, val path: String)
data class AppResponse(val name: String, val packageName: String, val installTime: String)
