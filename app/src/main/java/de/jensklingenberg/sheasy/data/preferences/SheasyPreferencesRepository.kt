package de.jensklingenberg.sheasy.data.preferences

import android.os.Environment
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.web.model.Device
import repository.SheasyPrefDataSource

class SheasyPreferencesRepository : SheasyPrefDataSource {


    override var appFolder= Environment.getExternalStorageDirectory().toString() + "/Sheasy/"

    override var acceptAllConnections= BuildConfig.DEBUG

    override val httpPort = 8766
    override val webSocketPort = 8765

    override val APIV1 = "/api/v1/"

    override val defaultPath = "/storage/emulated/0/"


    override val authorizedDevices = mutableListOf<Device>()

    override fun addAuthorizedDevice(device: Device) {
        authorizedDevices.add(device)
    }

    override fun removeDevice(device: Device) {
        authorizedDevices.remove(device)
    }
}