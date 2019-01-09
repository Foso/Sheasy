package network.ktor.repository

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.model.Device
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.devices.DevicesDataSource

class DesktopSheasyPrefDataSource(

) : SheasyPrefDataSource {
    override val devicesRepository: DevicesDataSource
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val sharedFolders = mutableListOf(FileResponse("/storage/emulated/0/","/storage/emulated/0/"))

    override var appFolder: String =""

    override var acceptAllConnections=true


    override val webSocketPort = 8765



    override val defaultPath = "/storage/emulated/0/"
    override val httpPort = 8766



}