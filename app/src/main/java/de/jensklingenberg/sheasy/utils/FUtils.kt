package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.webkit.MimeTypeMap
import de.jensklingenberg.sheasy.model.FileResponse
import fi.iki.elonen.NanoHTTPD
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * Created by jens on 25/2/18.
 */
data class ResponseFile(val fileInputStream: InputStream?, val mimeType: String?)

class FUtils {
    companion object {

        fun returnFile2(filePath: String): FileInputStream? {
            var fis: FileInputStream? = FileInputStream(filePath)

            return fis
        }

        fun returnFile(filePath: String): ResponseFile? {
            var fis: FileInputStream? = FileInputStream(filePath)

           return when (fis) {
               null -> {
                   null

               }
               else -> {
                   ResponseFile(FileInputStream(filePath), getMimeType(filePath)) }
           }
        }

        fun getMimeType(fileUrl: String): String {
            val extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)
            return NanoHTTPD.mimeTypes().get(extension) ?: "*"
        }

        fun returnAssetFile(context: Context, filePath: String): ResponseFile {
            val stream = context.assets.open(filePath)
           return ResponseFile(stream, getMimeType(filePath))
        }

        fun getFilesReponseList(folderPath: String): List<FileResponse> {
            val directory = File(folderPath)
            return directory.listFiles().map { FileResponse(it.name, it.path) }.toList()

        }

        fun returnAPK(context: Context, apkPackageName: String):ResponseFile? {
            AppUtils.getAllInstalledApplications(context).forEach {
                if (it.packageName == apkPackageName) {

                    val responseFile = ResponseFile(FileInputStream(it.sourceDir), getMimeType(it.sourceDir))
                    return responseFile
                }

            }
            return null
        }
    }


}