package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Response
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.Resource
import kotlinext.js.jsObject
import org.w3c.fetch.RequestInit
import org.w3c.files.File
import org.w3c.xhr.FormData
import kotlin.browser.window
import kotlin.js.json


class ReactHttpClient(private val networkPreferences: NetworkPreferences) : API {
    override fun downloadFile(path: String) {
        window.location.href =
            ApiEndPoint.fileDownloadUrl(path)

    }

    override fun downloadApk(packageName: String) {
        window.location.href =
            ApiEndPoint.appDownloadUrl(packageName)


    }

    override fun uploadFile(file: File, callback: ResponseCallback<Resource<State>>) {
        console.log(file.name)
        val formData = FormData()
        formData.append(file.name, file, file.name)

        window.fetch(networkPreferences.baseurl + ApiEndPoint.shared + "?upload=/", object : RequestInit {
            override var method: String? = "POST"
            override var body: dynamic = formData
            override var headers: dynamic = json("Accept" to "application/json","Content-Type" to "multipart/form-data")

        }).then {
            when (it.ok) {
                true -> {
                    callback.onSuccess(Resource.success(State.SUCCESS))
                    println(it)
                }

                else -> {
                    println(it)

                    callback.onError(Error.NetworkError())
                }
            }

        }

        callback.onError(Error.NetworkError())

    }

    override fun getFiles(folderPath: String, callback: ResponseCallback<List<FileResponse>>) {
        download(ApiEndPoint.getFiles(folderPath), callback)
    }

    override fun getShared(callback: ResponseCallback<List<FileResponse>>) {
        download(networkPreferences.baseurl + ApiEndPoint.shared, callback)
    }


    override fun getApps(callback: ResponseCallback<List<App>>) {
        download(networkPreferences.baseurl + ApiEndPoint.apps, callback)
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

                "NotAuthorizedError" -> calli.onError(Error.NotAuthorizedError())
                "ERROR" -> {
                    when (result.data.message) {
                        Error.NoSharedFoldersError().message -> {
                            calli.onError(Error.NoSharedFoldersError())

                        }
                        else -> calli.onError(Error.UNKNOWNERROR())

                    }
                }
                else -> calli.onError(Error.UNKNOWNERROR())
            }

        }.catch { error: Throwable ->
            calli.onError(Error.NetworkError())
        }
    }
}