package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.apiVersion
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.File
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.model.response.Resource
import de.jensklingenberg.sheasy.web.model.response.Response
import kotlinext.js.jsObject


class ReactHttpClient(private val networkPreferences: NetworkPreferences) : API {
    override fun uploadFile(file: File, callback: ResponseCallback<Resource<State>>) {


    }

    override fun getFiles(folderPath: String, callback: ResponseCallback<List<FileResponse>>) {
        val path = ApiEndPoint.getFiles(folderPath)

        download(path, callback)

    }

    override fun getShared(callback: ResponseCallback<List<FileResponse>>) {

        val path = networkPreferences.baseurl + ApiEndPoint.shared
        download(path, callback)

    }


    override fun getApps(callback: ResponseCallback<List<App>>) {

        val path = ApiEndPoint.apps

        download(networkPreferences.baseurl + path, callback)

    }

    private inline fun <reified T> download(path: String, calli: ResponseCallback<List<T>>) {


        Axios.get<Response<Array<T>>>(path, jsObject {
            timeout = 10000
        }).then { result ->
            when (result.data.status) {
                "SUCCESS" -> {
                    result.data.data?.let {
                        calli.onSuccess(it.toMutableList())
                    }

                }

                "NOT_AUTHORIZED" -> calli.onError(Error.NOT_AUTHORIZED)
                else -> calli.onError(Error.UNKNOWN_ERROR)
            }

        }.catch { error: Throwable ->
            calli.onError(Error.NETWORK_ERROR)
        }
    }
}