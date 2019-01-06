package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.model.AppInfo
import kotlinx.coroutines.rx2.await
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.Single
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject

class AndroidFileRouteHandler : FileRouteHandler {


    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun apps(httpMethod: HttpMethod): Single<List<AppInfo>> {
        return fileDataSource.getApps()
    }


    override fun postUpload(
        sourceFilePath: String,
        destinationFilePath: String,
        inputStream: InputStream
    ): Resource<Any> {

        val sourceFile = File(sourceFilePath)
        val destinationFile = File(destinationFilePath)

        sourceFile.copyTo(destinationFile, true)

        inputStream.copyTo(sourceFile.outputStream())

        return Resource.error("postUploadError","")

    }

    override suspend fun getShared(call: KtorApplicationCall): Resource<Any> {

       return Resource.success(sheasyPrefDataSource.sharedFolders)

    }

    override suspend fun getDownload(call: KtorApplicationCall): Resource<Any> {
        val filePath = call.parameter

        val allowedPath= sheasyPrefDataSource.sharedFolders.any { folderPath->
            folderPath.startsWith(filePath)
        }



        if (filePath.contains(".")) {

            val fileInputStream = FileInputStream(File(filePath))
            return Resource.success(fileInputStream.readBytes())

        } else {
            //appsRepository.sendBroadcast(EventCategory.REQUEST, filePath)

            val fileList = fileDataSource
                .getFiles(filePath)
                .await()

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

    override suspend fun apk(httpMethod: HttpMethod, call: KtorApplicationCall): Resource<Any> {
        val packageName = call.parameter

        fileDataSource
            .getApplicationInfo(packageName)
            .map { FileInputStream(it.sourceDir) }
            .await()?.let {
                return Resource.success(it.readBytes())
            }
        return Resource.error("getApkError","")

    }



    override fun get(call: KtorApplicationCall): Resource<Any> {
        when(call.requestedApiPath){

        }
        return Resource.error("Could not find command FileRoute", "")

    }


}