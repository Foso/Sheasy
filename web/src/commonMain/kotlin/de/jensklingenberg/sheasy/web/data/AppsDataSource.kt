package de.jensklingenberg.sheasy.web.data

import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.network.ResponseCallback


interface AppsDataSource{
    fun getApps(callback: ResponseCallback<List<App>>)
    fun getFiles(folderPath: String, callback: ResponseCallback<List<FileResponse>>)
}