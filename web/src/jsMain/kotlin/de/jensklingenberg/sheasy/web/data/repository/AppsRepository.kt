package de.jensklingenberg.sheasy.web.data.repository

import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.network.API
import de.jensklingenberg.sheasy.web.data.AppsDataSource


class AppsRepository(val api: API) : AppsDataSource {

    override fun getApps(onSuccess: (List<App>) -> Unit, onError: (de.jensklingenberg.sheasy.web.model.Error) -> Unit) {
        api.getApps(onSuccess, onError)
    }

    override fun getFiles(folderPath:String,onSuccess: (List<FileResponse>) -> Unit, onError: (de.jensklingenberg.sheasy.web.model.Error) -> Unit) {
        api.getFiles(folderPath,onSuccess,onError)
    }

}