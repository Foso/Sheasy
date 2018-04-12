package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.webkit.MimeTypeMap
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.model.FileResponse
import fi.iki.elonen.NanoHTTPD
import java.io.File
import java.io.FileInputStream

/**
 * Created by jens on 25/2/18.
 */

class FUtils {
    companion object {
        fun returnFile(filePath: String): NanoHTTPD.Response? {
            var fis: FileInputStream? = FileInputStream(filePath)


val response = NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, getMimeType(filePath), fis)
val fileName = "Hallo.txt"
            response.addHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
            return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, "hallo.txt", fis)
        }

        fun getMimeType(fileUrl: String): String {
            val extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)
            return NanoHTTPD.mimeTypes().get(extension) ?: "*"
        }

        fun returnAssetFile(context: Context, filePath: String): NanoHTTPD.Response? {
            val stream = context.assets.open(filePath)
            return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, getMimeType(filePath), stream)
        }

        fun getFilesResponse(folderPath: String): String {
            val directory = File(folderPath)
            val files = directory.listFiles().map { FileResponse(it.name,it.path) }.toList()
            val moshi = Moshi.Builder().build()
            val listMyData = Types.newParameterizedType(List::class.java, FileResponse::class.java)
            val adapter = moshi.adapter<List<FileResponse>>(listMyData)
            return adapter.toJson(files)

        }

        fun handleApkDowload(context: Context,apkPackageName: String): NanoHTTPD.Response? {
            AppUtils.getAllInstalledApplications(context).forEach {
                if(it.packageName == apkPackageName){
                    return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, getMimeType(it.sourceDir), FileInputStream(it.sourceDir))
                }
            }
            return NanoHTTPD.newFixedLengthResponse("Package Not Found")
        }
    }


}