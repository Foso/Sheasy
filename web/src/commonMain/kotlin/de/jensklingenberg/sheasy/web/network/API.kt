package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse


interface API {
    // fun<T> getApps():T
    fun getApps(onSuccess: (List<App>) -> Unit, onError: (de.jensklingenberg.sheasy.web.model.Error) -> Unit)
    fun getFiles(folderPath:String, onSuccess: (List<FileResponse>) -> Unit, onError: (de.jensklingenberg.sheasy.web.model.Error) -> Unit)
}
