package network.ktor



import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import io.reactivex.Single
import kotlinx.coroutines.rx2.await
import main.MockTestDataSource.Companion.mockAppList
import network.network.ktor.repository.FileRepository
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class DesktopFileRouteHandler(val fileDataSource: FileRepository) : FileRouteHandler {


    override fun getApps(): Single<List<AppInfo>> {

        return Single.just(mockAppList)
    }

    override suspend fun apk( packageName:String): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }







    override suspend fun getDownload(filePath : String): Resource<Any> {


        if (filePath.contains(".")) {

            val fileInputStream = FileInputStream(File(filePath))
            return Resource.success(fileInputStream.readBytes())

        } else {
            //appsRepository.sendBroadcast(EventCategory.REQUEST, filePath)

            val fileList = fileDataSource
                .getFiles(filePath)
                .await()

            if (fileList.isEmpty()) {
                return Resource.error("path not found", "")


            }

            return Resource.error("getDownloadError", "")

        }
    }

    override suspend fun getShared(call: KtorApplicationCall): Resource<Any> {
        if(call.parameter.contains("/storage")){
            call.parameter="/"
        }
        val files = File(call.parameter)
            .listFiles()
            ?.map {
                FileResponse(
                    it.name,
                    it.path
                )
            } ?: emptyList()
        return Resource.error("NoSharedFoldersError",SheasyError.NoSharedFoldersError().message)


    }

    override fun postUpload(
        sourceFilePath: String,
        destinationFilePath: String,
        inputStream: InputStream
    ): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}