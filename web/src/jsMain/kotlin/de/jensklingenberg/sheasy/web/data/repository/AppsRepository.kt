package de.jensklingenberg.sheasy.web.data.repository

import de.jensklingenberg.sheasy.web.data.AppsDataSource
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.network.API
import de.jensklingenberg.sheasy.web.network.ResponseCallback


class AppsRepository(val api: API) : AppsDataSource {
    override fun getFiles(folderPath: String, callback: ResponseCallback<List<FileResponse>>) {
        api.getFiles(folderPath,callback)

    }

    override fun getApps(callback: ResponseCallback<List<App>>) {
        api.getApps(callback)
    }


}