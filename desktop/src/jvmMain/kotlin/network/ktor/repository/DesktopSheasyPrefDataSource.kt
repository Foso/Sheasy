package network.ktor.repository

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.devices.DevicesDataSource

class DesktopSheasyPrefDataSource(

) : SheasyPrefDataSource {
    override val sharedFolders: ArrayList<FileResponse>
        get() = arrayListOf(FileResponse("/storage/emulated/0/","/storage/emulated/0/"))



    override val nonInterceptedFolders: List<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val devicesRepository: DevicesDataSource
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override var appFolder: String =""

    override var acceptAllConnections=true


    override val webSocketPort = 8765



    override val defaultPath = "/storage/emulated/0/"
    override val httpPort = 8766



}