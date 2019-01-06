package network.ktor.repository

import de.jensklingenberg.sheasy.web.model.Device
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource

class DesktopSheasyPrefDataSource(

) : SheasyPrefDataSource {

    override var appFolder: String =""

    override var acceptAllConnections=true


    override val webSocketPort = 8765



    override val defaultPath = "/storage/emulated/0/"
    override val httpPort = 8766


    override val authorizedDevices = mutableListOf<Device>()
    override fun addAuthorizedDevice(device: Device) {

    }

    override fun removeDevice(device: Device) {
    }

}