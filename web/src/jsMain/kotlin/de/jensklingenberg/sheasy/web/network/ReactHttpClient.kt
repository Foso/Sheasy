package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.apiVersion
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.model.response.Response
import kotlinext.js.jsObject

class ReactHttpClient() : API {

    val baseurl = "http://${NetworkPreferences().hostname}/api/$apiVersion/"


    override fun getFiles(folderPath: String, onSuccess: (List<FileResponse>) -> Unit, onError: (Error) -> Unit) {
        val path = ApiEndPoint.files + folderPath
        Axios.get<Response<Array<FileResponse>>>(baseurl + path, jsObject {
            timeout = 10000
        }).then { result ->
            if (result.data.status == "SUCCESS") {
                onSuccess(result.data.data!!.toMutableList())
            } else {
                onError(Error.NOT_AUTHORIZED)
            }

        }.catch { error: Throwable ->
            onError(Error.NETWORK_ERROR)
        }
    }


    override fun getApps(onSuccess: (List<App>) -> Unit, onError: (Error) -> Unit) {
        val path = ApiEndPoint.apps

        Axios.get<Response<Array<App>>>(baseurl + path, jsObject {
            timeout = 10000
        }).then { result ->
            if (result.data.status == "SUCCESS") {
                onSuccess(result.data.data!!.toMutableList())
            } else {
                onError(Error.NOT_AUTHORIZED)
            }

        }.catch { error: Throwable ->
            onError(Error.NETWORK_ERROR)
        }
    }
}