package de.jensklingenberg.sheasy.handler

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.ApiFileCommand
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import de.jensklingenberg.sheasy.extension.getParameterQueryMap
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.utils.FUtils
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.ResponseException
import java.io.File
import java.io.IOException


/**
 * Created by jens on 14/2/18.
 */

class FileRequestHandler {


    companion object {

        val RESOURCE = "/file/"


        fun handleRequest(session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {

            val query = session.queryParameterString

            when (session.method) {

                NanoHTTPD.Method.GET -> {
                    return handleGET(App.instance, session, query)
                }

                NanoHTTPD.Method.POST -> {
                    return handlePOST(App.instance, session)
                }

                else -> {
                    return NanoHTTPD.newFixedLengthResponse("FileCommand $query not found")

                }
            }


        }

        private fun handlePOST(app: App, session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
            var files: Map<String, String> = HashMap()
            val destinationPath = session.getParameterQueryMap()["file"]

            try {
                session.parseBody(files)
                val sourceFile = File(files["file"])
                val destinationFile = File(destinationPath)
                sourceFile.copyTo(destinationFile, true)
                app.sendBroadcast(
                    EventCategory.REQUEST,
                    "File Received ${destinationFile.name}to: $destinationPath"
                )
                return NanoHTTPDExt.debugResponse("OK")

            } catch (ioe: IOException) {
                Log.d("this", "SERVER INTERNAL ERROR: IOException: " + ioe.message)
                return NanoHTTPDExt.debugResponse("ERROR")

            } catch (re: ResponseException) {
                Log.d("this", "SERVER INTERNAL E2RROR: IOException: " + re.message)
                return NanoHTTPDExt.debugResponse("ERROR")

            }
        }

        private fun handleGET(
            app: App,
            session: NanoHTTPD.IHTTPSession,
            query: String
        ): NanoHTTPD.Response? {
            var map = session.getParameterQueryMap()
            val apiFileCommand = ApiFileCommand.get(query.substringBefore("="))
            when (apiFileCommand) {
                ApiFileCommand.APK -> {
                    val apkPackageName = map["apk"] ?: ""
                    app.sendBroadcast("APK Requested", apkPackageName)
                    val handleApkDowload = FUtils.returnAPK(app, apkPackageName)

                    return when (handleApkDowload) {
                        null -> {
                            NanoHTTPD.newFixedLengthResponse("APK $query not found")

                        }
                        else -> {
                            val (fileInputStream, mimeType) = handleApkDowload
                            NanoHTTPD.newChunkedResponse(
                                NanoHTTPD.Response.Status.OK,
                                mimeType,
                                fileInputStream
                            )
                        }
                    }


                }
                ApiFileCommand.INVALID -> {
                    return NanoHTTPD.newFixedLengthResponse("DeviceCommand ${session.uri} not found")
                }
                ApiFileCommand.FILE -> {
                    val pathEnding = session.queryParameterString.substringAfterLast("/")
                    return if (pathEnding.contains(".")) {
                        val filePath = map["file"] ?: ""
                        app.sendBroadcast("File Requested", filePath)
                        val returnFile = FUtils.returnFile(filePath)

                        returnFile?.let {
                            val (fileInputStream, mimeType) = it
                            val response = NanoHTTPD.newChunkedResponse(
                                NanoHTTPD.Response.Status.OK,
                                FUtils.getMimeType(filePath),
                                fileInputStream
                            )
                            val fileName = "Hallo.txt"
                            response.addHeader(
                                "Content-Disposition",
                                "attachment; filename=\"" + fileName + "\""
                            )
                            return response
                        }

                        return NanoHTTPD.newFixedLengthResponse("File $query not found")


                    } else {
                        val folderPath = map["file"] ?: ""
                        app.sendBroadcast("FilePath Requested", folderPath)

                        val fileList = FUtils.getFilesReponseList(folderPath)
                        val moshi = Moshi.Builder().build()
                        val listMyData =
                            Types.newParameterizedType(List::class.java, FileResponse::class.java)
                        val adapter = moshi.adapter<List<FileResponse>>(listMyData)

                        return NanoHTTPDExt.debugResponse(adapter.toJson(fileList))
                    }
                }
            }
        }
    }


}


