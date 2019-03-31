package network.network.ktor.repository

import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import io.reactivex.Single
import java.io.File
import java.io.InputStream

class FileRepository : FileDataSource {
    override fun getFiles(folderPath: String): Single<List<FileResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAssetFile(filePath: String): Single<InputStream> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getApps(packageName: String): Single<List<AppInfo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun extractApk(appInfo: AppInfo): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTempFile(appInfo: AppInfo): File {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}