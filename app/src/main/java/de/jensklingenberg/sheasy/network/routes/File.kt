package de.jensklingenberg.sheasy.network.routes

import de.jensklingenberg.sheasy.utils.FileRepository
import de.jensklingenberg.sheasy.utils.IAppsRepostitoy
import io.ktor.application.call
import io.ktor.http.ContentDisposition
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import java.io.File
import java.io.FileInputStream


fun Route.file(
    appsRepository: IAppsRepostitoy) {
    route("file") {
        param("apk") {
            get {

                val packageName = call.parameters["apk"] ?: ""

                val apk = appsRepository.returnAPK(packageName)
                val fileInputStream = FileInputStream(apk?.sourceDir)
                with(call) {
                    response.header(
                        HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(
                            ContentDisposition.Parameters.FileName,
                            "$packageName.apk"
                        ).toString()
                    )
                    respond(fileInputStream.readBytes())
                }


            }
        }

        param("upload") {
            post {
                val filePath = call.parameters["upload"] ?: ""


                val multipart = call.receiveMultipart()
                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {

                        }
                        is PartData.FileItem -> {
                            val ext = File(part.originalFileName).extension

                            val sourceFile = File(filePath)
                            val destinationFile = File(filePath)
                            sourceFile.copyTo(destinationFile, true)

                            part.streamProvider().use { its ->
                                its.copyTo(sourceFile.outputStream())
                            }
                        }

                    }

                }
            }
        }


        param("download") {
            get {
                val filePath = call.parameters["download"] ?: ""

                if (filePath.startsWith("/storage/emulated/0/") == false) {
                    call.respondText(
                        "path not allowed",
                        ContentType.Text.JavaScript
                    )
                }


                if (filePath.contains(".")) {

                    val fileInputStream = FileInputStream(File(filePath))

                    call.respond(
                        fileInputStream.readBytes()
                    )
                } else {
                    //appsRepository.sendBroadcast(EventCategory.REQUEST, filePath)

                    val fileList = FileRepository.getFilesReponseList(filePath)

                    if (fileList.isEmpty()) {
                        call.respondText(
                            "path not found",
                            ContentType.Text.JavaScript
                        )

                    } else {


                        call.apply {
                            response.header(
                                HttpHeaders.AccessControlAllowOrigin,
                                "*"
                            )
                            respond(fileList)
                        }

                    }


                }

            }
        }


        param("shared") {
            get {
                val shared = "/storage/emulated/0/Music"

                val filePath = call.parameters["shared"] ?: ""

                if (filePath.startsWith(shared) == false) {
                    call.respondText(
                        "path not allowed",
                        ContentType.Text.JavaScript
                    )
                }


                if (filePath.contains(".")) {

                    val fileInputStream = FileInputStream(File(filePath))

                    call.respond(
                        fileInputStream.readBytes()
                    )
                } else {

                    val fileList = FileRepository.getFilesReponseList(filePath)

                    if (fileList.isEmpty()) {
                        call.respondText(
                            "path not found",
                            ContentType.Text.JavaScript
                        )

                    } else {


                        call.apply {
                            response.header(
                                HttpHeaders.AccessControlAllowOrigin,
                                "*"
                            )


                            respond(fileList)
                        }

                    }


                }

            }
        }

    }
}

