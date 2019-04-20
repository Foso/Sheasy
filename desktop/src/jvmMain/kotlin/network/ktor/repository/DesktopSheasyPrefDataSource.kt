package network.ktor.repository

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.data.DevicesDataSource
import io.reactivex.subjects.BehaviorSubject

class DesktopSheasyPrefDataSource(

) : SheasyPrefDataSource {
    override fun addShareFolder(folder: FileResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeSharedFolders(): BehaviorSubject<List<FileResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeShareFolder(folder: FileResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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