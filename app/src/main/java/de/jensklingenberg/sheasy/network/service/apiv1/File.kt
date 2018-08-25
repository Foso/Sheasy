package de.jensklingenberg.sheasy.network.service.apiv1

import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.extension.toJson
import io.ktor.application.call
import io.ktor.content.PartData
import io.ktor.content.forEachPart
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.request.receiveMultipart
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import java.io.File
import java.io.FileInputStream


fun Route.file(app: App, moshi: Moshi, httpServerService: HTTPServerService) {
    route("file") {


        param("apk") {
            get {
                val packageName = call.parameters["apk"] ?: ""

                val test = FUtils.returnAPK(app, packageName)
                val mimeType = test?.mimeType
                val fileInputStream = test?.fileInputStream

                call.respond(fileInputStream?.readBytes() ?: "apk Not found")
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

                val newList = httpServerService.sharedFolder.filter {
                    if (filePath.startsWith(it)) {
                        return@filter true
                    }
                    return@filter false
                }
                if (newList.isEmpty()) {
                    call.respondText(
                        "path not allowed",
                        ContentType.Text.JavaScript
                    )
                }


                if (filePath.contains(".")) {

                    val test = FUtils.returnAPK(app, filePath)
                    val mimeType = test?.mimeType
                    val fileInputStream = FileInputStream(File(filePath))

                    call.respond(
                        fileInputStream.readBytes()
                    )
                } else {
                    app.sendBroadcast(EventCategory.REQUEST, filePath)

                    val fileList = FUtils.getFilesReponseList(filePath)

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
                            respondText(
                                moshi.toJson(fileList),
                                ContentType.Text.JavaScript
                            )
                        }

                    }


                }

            }
        }
    }
}
