package de.jensklingenberg.sheasy.network.ktor.routehandler

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.HttpMethod
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import io.reactivex.Single
import kotlinx.coroutines.rx2.await
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

        return Resource.error("postUploadError", "")

    }

    override suspend fun getShared(call: KtorApplicationCall): Resource<Any> {

        val filePath = call.parameter

        if (filePath.isEmpty()) {

            if (sheasyPrefDataSource.sharedFolders.isEmpty()) {
                return Resource.error(SheasyError.NoSharedFoldersError().message, "")

            } else {
                return Resource.success(sheasyPrefDataSource.sharedFolders)
            }
        } else {

            val allowedPath = sheasyPrefDataSource.sharedFolders.any { folderPath ->
                filePath.startsWith(folderPath.path)
            }

            if (allowedPath) {
                val fileList = fileDataSource
                    .getFiles(call.parameter)
                    .await()

                return Resource.success(fileList)
            } else {
                return Resource.success(sheasyPrefDataSource.sharedFolders)
            }
        }
    }

    override suspend fun getDownload(filePath: String): Resource<Any> {







        val allowedPath = sheasyPrefDataSource.sharedFolders.any { folderPath ->
            folderPath.path.startsWith(filePath)
        }



        if (filePath.contains(".")) {

            val fileInputStream = FileInputStream(File(filePath))
            return Resource.success(fileInputStream.readBytes())

        } else {
            val fileList = fileDataSource
                .getFiles(filePath)
                .await()

            if (fileList.isEmpty()) {
                return Resource.error("path not found", "")


            }

            return Resource.error("getDownloadError", "")

        }
    }

    override suspend fun apk( packageName: String): Resource<Any> {
        fileDataSource
            .getApps(packageName)
            .await()
            .firstOrNull()
            ?.let {
                return Resource.success(FileInputStream(it.sourceDir).readBytes())
            }
        return Resource.error("Could not find command FileRoute", "")
    }





}