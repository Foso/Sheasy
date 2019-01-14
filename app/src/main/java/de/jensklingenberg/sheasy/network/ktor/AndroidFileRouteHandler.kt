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

    /**
     *
     * List all glossaries the current user has access to.
     * @param accountId AccountId
     * @param perPage Per Page (optional, default to 25)
     * @param page Page (optional, default to 1)
     * @return kotlin.Array<Glossary>
     */
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

    override suspend fun getShared(call: KtorApplicationCall): Resource<Any> = Resource.success(sheasyPrefDataSource.sharedFolders)

    override suspend fun getDownload(call: KtorApplicationCall): Resource<Any> {
        val filePath = call.parameter

        val allowedPath= sheasyPrefDataSource.sharedFolders.any { folderPath->
            folderPath.path.startsWith(filePath)
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
            .getApps(packageName)
            .await()
            .firstOrNull()
            ?.let {
                return Resource.success(FileInputStream(it.sourceDir).readBytes())
            }
    }



    override fun get(call: KtorApplicationCall): Resource<Any> {
        when(call.requestedApiPath){

        }
        return Resource.error("Could not find command FileRoute", "")

    }


}