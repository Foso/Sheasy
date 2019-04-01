package de.jensklingenberg.sheasy.data.preferences

import android.os.Environment
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.data.devices.DevicesRepository
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource

class SheasyPreferencesRepository : SheasyPrefDataSource {
    override val nonInterceptedFolders: List<String> = listOf("/web/connection/")
    override val devicesRepository = DevicesRepository()


    override var appFolder= Environment.getExternalStorageDirectory().toString() + "/Sheasy/"

    override var acceptAllConnections= true

    override val sharedFolders = arrayListOf<FileResponse>()


    override val httpPort = 8766
    override val webSocketPort = 8765

    override val defaultPath = "/storage/emulated/0/"




}