package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.model.File
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.model.response.Resource


interface API {
    fun getApps(callback: ResponseCallback<List<App>>)
    fun getFiles(
        folderPath: String,
        callback: de.jensklingenberg.sheasy.web.network.ResponseCallback<List<FileResponse>>
    )
    fun  uploadFile(file:File,callback: ResponseCallback<Resource<State>>)
    fun getShared(
        callback: de.jensklingenberg.sheasy.web.network.ResponseCallback<List<FileResponse>>
    )
}
