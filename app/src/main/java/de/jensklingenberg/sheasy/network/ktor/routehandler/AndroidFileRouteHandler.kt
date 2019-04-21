package de.jensklingenberg.sheasy.network.ktor.routehandler

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.ktor.KtorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.utils.extension.debugCorsHeader
import io.ktor.application.call
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.*
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
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


    private fun getShared(): Single<List<FileResponse>> = Single.create<List<FileResponse>> { singleEmitter ->
        if (sheasyPrefDataSource.sharedFolders.isEmpty()) {
            singleEmitter.onError(SheasyError.NoSharedFoldersError())
        } else {
            singleEmitter.onSuccess(sheasyPrefDataSource.sharedFolders)
        }
    }


    private fun getFilesList(call: KtorApplicationCall): Single<List<FileResponse>> {
        return Single.create<List<FileResponse>> { singleEmitter ->
            val filePath = call.parameter

            if (filePath.isEmpty()) {

                if (sheasyPrefDataSource.sharedFolders.isEmpty()) {
                    singleEmitter.onError(SheasyError.NoSharedFoldersError())

                } else {
                    singleEmitter.onSuccess(sheasyPrefDataSource.sharedFolders)
                }
            } else {

                val allowedPath = sheasyPrefDataSource.sharedFolders.any { folderPath ->
                    filePath.startsWith(folderPath.path)
                }

                if (allowedPath) {
                    fileDataSource
                        .observeFiles(call.parameter)
                        .subscribeBy(onSuccess = {
                            singleEmitter.onSuccess(it)
                        })
                } else {
                    singleEmitter.onSuccess(sheasyPrefDataSource.sharedFolders)
                }
            }


        }
    }

    private fun getFile(filePath: String): Single<InputStream> = Single.create<InputStream> { singleEmitter ->

        if (filePath.contains(".")) {

            fileDataSource.getFile(filePath).subscribeBy(
                onSuccess = {
                    singleEmitter.onSuccess(it)

                }
            )

        } else {
            fileDataSource
                .observeFiles(filePath)
                .subscribeBy(
                    onSuccess = { fileList ->
                        if (fileList.isEmpty()) {
                            singleEmitter.onError(SheasyError.PathNotFoundError())
                        }

                        singleEmitter.onError(SheasyError.PathNotFoundError())
                    }
                )
        }
    }

    override fun handleRoute(route: Route) {
        with(route) {
            /**
             * GET
             */
            route("file") {
                get("apps") {
                    //"file/apps"
                    fileDataSource.getApps()
                        .doOnError {
                            if (it is SheasyError) {
                                launch {
                                    call.response.debugCorsHeader()
                                    call.respond(Resource.error<SheasyError>(it))
                                }
                            }
                        }
                        .await()
                        .run {
                            call.response.debugCorsHeader()
                            call.respond(Resource.success(this.map{AppInfo(it.sourceDir,it.name,it.packageName,it.installTime)}))
                        }
                }

                route("app") {
                    param("package") {
                        get {
                            val packageName = call.parameters["package"] ?: ""

                            fileDataSource
                                .getApps(packageName)
                                .doOnError {error->
                                    if (error is SheasyError) {
                                        launch {
                                            call.response.debugCorsHeader()
                                            call.respond(Resource.error<SheasyError>(error))
                                        }
                                    }
                                }
                                .await()
                                .run {

                                    this.first { it.packageName.equals(packageName) }
                                        .run {
                                            call.response.header(
                                                HttpHeaders.ContentDisposition,
                                                ContentDisposition.Attachment.withParameter(
                                                    ContentDisposition.Parameters.FileName,
                                                    "$packageName.apk"
                                                ).toString()
                                            )
                                            call.respond(FileInputStream(this.sourceDir).readBytes())
                                        }
                                }
                        }
                    }
                }

                route("file") {
                    param("path") {
                        get {
                            val filepath = call.parameters["path"] ?: ""
                            getFile(filepath)
                                .doOnError {
                                    if (it is SheasyError) {
                                        launch {
                                            call.response.debugCorsHeader()
                                            call.respond(Resource.error<SheasyError>(it))
                                        }
                                    }
                                }
                                .await()

                                .run {
                                    call.response.header(
                                        HttpHeaders.ContentDisposition,
                                        ContentDisposition.Attachment.withParameter(
                                            ContentDisposition.Parameters.FileName,
                                            value = filepath.substringAfterLast(delimiter = "/")
                                        ).toString()
                                    )
                                    call.respond(this.readBytes())
                                }
                        }

                    }
                }


                route("folder") {
                    param("path") {
                        get {
                            val filePath = call.parameters["path"] ?: ""

                            val ktor = call.ktorApplicationCall(filePath).apply {
                                parameter = filePath
                            }

                            getFilesList(ktor)
                                .doOnError {
                                    if (it is SheasyError) {
                                        launch {
                                            call.response.debugCorsHeader()
                                            call.respond(Resource.error<SheasyError>(it))
                                        }
                                    }
                                }
                                .await()
                                .run {
                                    call.response.debugCorsHeader()
                                    call.respond(Resource.success(this))
                                }
                        }

                    }
                }

                route("shared") {
                    get {
                        getShared()
                            .doOnError {error->
                                if (error is SheasyError) {
                                    launch {
                                        call.response.debugCorsHeader()
                                        call.respond(Resource.error<SheasyError>(error))
                                    }
                                }
                            }
                            .await()
                            .run {
                                call.response.debugCorsHeader()
                                call.respond(Resource.success(this))
                            }


                    }

                    /**
                     * POST
                     */
                    param("upload") {
                        post {
                            val filePath2 = call.parameters["upload"] ?: ""

                            val multipart = call.receiveMultipart()
                            multipart.forEachPart { part ->
                                when (part) {
                                    is PartData.FileItem -> {
                                        val sourceFilePath = filePath2 + part.originalFileName
                                        part.streamProvider().use { its ->
                                            fileDataSource.saveUploadedFile(sourceFilePath, its)
                                                .doOnError {
                                                    if (it is SheasyError) {
                                                        launch {
                                                            call.response.debugCorsHeader()
                                                            call.respond(Resource.error<SheasyError>(it))
                                                        }
                                                    }
                                                }
                                                .await()
                                                .run {
                                                    call.response.debugCorsHeader()
                                                    call.respond(Resource.success(this))
                                                }
                                        }


                                    }

                                    else -> {

                                    }
                                }

                            }

                        }
                    }


                }
            }
        }

    }


}





