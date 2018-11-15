package de.jensklingenberg.sheasy.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import de.jensklingenberg.sheasy.App
import model.FileResponse
import java.io.File
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