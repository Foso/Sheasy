package de.jensklingenberg.sheasy.handler

import android.content.Context
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.enums.ApiFileCommand
import de.jensklingenberg.sheasy.extension.getParameterQueryMap
import de.jensklingenberg.sheasy.utils.FUtils
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoHTTPD.ResponseException
import java.io.IOException
import de.jensklingenberg.sheasy.extension.NanoHTTPDExt
import java.io.File


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
                    return handleGET(App.instance,session,query)
                }

                NanoHTTPD.Method.POST -> {
                    return handlePOST(App.instance,session)
                }

                else -> {
                    return NanoHTTPD.newFixedLengthResponse("FileCommand $query not found")

                }
            }


        }

        private fun handlePOST(app: App,session: NanoHTTPD.IHTTPSession): NanoHTTPD.Response? {
            var files: Map<String, String> = HashMap()
            val destinationPath = session.getParameterQueryMap()["file"]

            try {
                session.parseBody(files)
                val sourceFile = File(files["file"])
                val destinationFile = File(destinationPath)
                sourceFile.copyTo(destinationFile, true)
                app.sendBroadcast("File Received","${destinationFile.name}to: $destinationPath")
                return NanoHTTPDExt.debugResponse("OK")

            } catch (ioe: IOException) {
                Log.d("this", "SERVER INTERNAL ERROR: IOException: " + ioe.message)
                return NanoHTTPDExt.debugResponse("ERROR")

            } catch (re: ResponseException) {
                Log.d("this", "SERVER INTERNAL E2RROR: IOException: " + re.message)
                return NanoHTTPDExt.debugResponse("ERROR")

            }
        }

        private fun handleGET(app: App,session: NanoHTTPD.IHTTPSession, query: String): NanoHTTPD.Response? {
            var map = session.getParameterQueryMap()
            val apiFileCommand = ApiFileCommand.get(query.substringBefore("="))
            when (apiFileCommand) {
                ApiFileCommand.APK -> {
                    val apkPackageName = map["apk"] ?: ""
                    app.sendBroadcast("APK Requested", apkPackageName)
                    return FUtils.handleApkDowload(app,apkPackageName)
                }
                ApiFileCommand.UNKNOWN -> {
                    return NanoHTTPD.newFixedLengthResponse("DeviceCommand ${session.uri} not found")
                }
                ApiFileCommand.FILE -> {
                    val pathEnding = session.queryParameterString.substringAfterLast("/")
                    return if (pathEnding.contains(".")) {
                        val filePath= map["file"] ?: ""
                        app.sendBroadcast("File Requested",filePath)
                        FUtils.returnFile(filePath)
                    } else {
                        val folderPath= map["file"] ?: ""
                        app.sendBroadcast("FilePath Requested",folderPath)
                        return NanoHTTPDExt.debugResponse(FUtils.getFilesResponse(folderPath))
                    }
                }
            }
        }
    }


}


