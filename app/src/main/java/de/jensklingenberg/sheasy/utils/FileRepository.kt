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
    lateinit var context: Context

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)




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