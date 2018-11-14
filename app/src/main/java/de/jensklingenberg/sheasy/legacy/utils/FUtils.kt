package de.jensklingenberg.sheasy.legacy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.webkit.MimeTypeMap
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.ResponseFile
import fi.iki.elonen.NanoHTTPD
import model.FileResponse
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

/**
 * Created by jens on 25/2/18.
 */

class FUtils {

    @Inject
    lateinit var appUtils: AppUtils

    @Inject
    lateinit var context: Context

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.oldAppComponent.inject(this)


    fun returnAPK(apkPackageName: String): ApplicationInfo? {
        return appUtils.returnAPK(apkPackageName)
    }

    fun returnAssetFile(filePath: String) = context.assets.open(filePath)


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
                    ResponseFile(FileInputStream(filePath), getMimeType(filePath))
                }
            }
        }

        fun getMimeType(fileUrl: String): String {
            val extension = MimeTypeMap.getFileExtensionFromUrl(fileUrl)
            return NanoHTTPD.mimeTypes().get(extension) ?: "*"
        }


        fun getFilesReponseList(folderPath: String): List<FileResponse> {
            val directory = File(folderPath)
            return directory.listFiles()?.map {
                FileResponse(
                    it.name,
                    it.path
                )
            }?.toList()
                ?: emptyList()

        }


    }


}