package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.model.response.Resource
import org.w3c.files.File


interface API {
    fun getApps(callback: ResponseCallback<List<App>>)
    fun getFiles(
        folderPath: String,
        callback: ResponseCallback<List<FileResponse>>
    )
    fun  uploadFile(file: File, callback: ResponseCallback<Resource<State>>)
    fun getShared(
        callback: ResponseCallback<List<FileResponse>>
    )
}
