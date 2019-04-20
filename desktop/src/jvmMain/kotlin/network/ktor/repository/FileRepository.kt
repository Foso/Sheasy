package network.network.ktor.repository

import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.InputStream

class FileRepository : FileDataSource {
    override fun extractApk(appInfo: AppInfo): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTempFile(appInfo: AppInfo): File {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUploadedFile(destinationFilePath: String, inputStream: InputStream):Completable{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFile(filePath: String, isAssetFile: Boolean): Single<InputStream> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeFiles(folderPath: String): Single<List<FileResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    override fun getApps(packageName: String): Single<List<AppInfo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}