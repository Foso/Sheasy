package de.jensklingenberg.sheasy.web.data

import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.FileResponse


interface AppsDataSource{
    fun getApps(onSuccess: (List<App>)->Unit, onError: (Error)->Unit)
    fun getFiles(folderPath: String, onSuccess: (List<FileResponse>) -> Unit, onError: (Error) -> Unit)
}