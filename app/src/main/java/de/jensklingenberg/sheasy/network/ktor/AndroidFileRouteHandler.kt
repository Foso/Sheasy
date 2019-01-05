package de.jensklingenberg.sheasy.network.ktor

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.web.model.AppInfo
import kotlinx.coroutines.rx2.await
import de.jensklingenberg.sheasy.web.model.AppResponse
import de.jensklingenberg.sheasy.web.model.Resource
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject

class AndroidFileRouteHandler : FileRouteHandler {


    @Inject
    lateinit var fileDataSource: FileDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getApps(): Single<List<AppInfo>> = fileDataSource.getApps()


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
        val shared = "/storage/emulated/0/Music"

        val filePath = call.parameter

        if (!filePath.startsWith(shared)) {
            return Resource.error("path not allowed","")
        }


        if (filePath.contains(".")) {

            val fileInputStream = FileInputStream(File(filePath))

            return Resource.success(fileInputStream.readBytes())

        } else {

            val fileList = fileDataSource
                .getFiles(filePath)
                .await()

            if (fileList.isEmpty()) {
                return Resource.error("path not found","")


            } else {


                call.apply {

                    return Resource.success(fileList)
                }

            }


        }

    }

    override suspend fun getDownload(call: KtorApplicationCall): Resource<Any> {
        val filePath = call.parameter

        if (filePath.startsWith("/storage/emulated/0/") == false) {

            return Resource.error("path not allowed","")
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
                with(call) {
                    return Resource.success(it.readBytes())
                }
            }
        return Resource.error("getApkError","")

    }



    override fun get(call: KtorApplicationCall): Resource<Any> {
        when(call.requestedApiPath){

        }
        return Resource.error("Could not find command FileRoute", "")

    }


}