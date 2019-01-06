package network.ktor

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import io.reactivex.Single
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class DesktopFileRouteHandler: FileRouteHandler {
    override suspend fun apk(httpMethod: HttpMethod, call: KtorApplicationCall): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(call: KtorApplicationCall): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun apps(httpMethod: HttpMethod): Single<List<AppInfo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


    }



    override suspend fun getDownload(call: KtorApplicationCall): Resource<Any> {
        val filePath = call.parameter




        if (filePath.contains(".")) {

            val fileInputStream = FileInputStream(File(filePath))
            return Resource.success(fileInputStream.readBytes())

        } else {
            //appsRepository.sendBroadcast(EventCategory.REQUEST, filePath)

            val fileList = File(call.parameter).listFiles()


            if (fileList.isEmpty()) {
                return Resource.error("path not found","")


            } else {


                call.apply {
                    Resource.success(fileList)
                }

            }


        }
        return Resource.error("getDownloadError","")

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
        return Resource.success(files)


    }

    override fun postUpload(
        sourceFilePath: String,
        destinationFilePath: String,
        inputStream: InputStream
    ): Resource<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}