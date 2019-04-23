package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.Response
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import kodando.rxjs.Observable
import kotlinext.js.jsObject
import network.SharedNetworkSettings
import org.w3c.fetch.RequestInit
import org.w3c.files.File
import org.w3c.xhr.FormData
import kotlin.browser.window
import kotlin.js.json


class ReactHttpClient() : HttpAPI {
    val networkSettings = SharedNetworkSettings(NetworkPreferences().baseurl)


    override fun fileDownloadUrl(path: String): String {
        return networkSettings.fileDownloadUrl(path)

    }

    override fun postUploadUrl(folderPath: String): String {
        return networkSettings.postUploadUrl(folderPath)
    }

    override fun getSharedFoldersUrl(): String {
        return networkSettings.getSharedFoldersUrl()

    }

    override fun getFilesUrl(path: String): String {
        return networkSettings.getFilesUrl(path)
    }


    override fun getRepoSite(): String {
        return networkSettings.getRepoSite()
    }

    override fun getAppsUrl(): String {
        return networkSettings.getAppsUrl()
    }

    override fun appDownloadUrl(packageName: String): String {
        return networkSettings.appDownloadUrl(packageName)
    }


    override fun downloadFile(path: String) {
        window.location.href =
            SharedNetworkSettings(NetworkPreferences().baseurl).fileDownloadUrl(path)
    }

    override fun downloadApk(packageName: String) {
        window.location.href =
            networkSettings.appDownloadUrl(packageName)
    }

    override fun uploadFile(file: File, folderPath: String): Observable<Resource<State>> {

        return Observable { observer ->
            val formData = FormData()
            formData.append(file.name, file, file.name)

            window.fetch(
                networkSettings.postUploadUrl(folderPath),
                object : RequestInit {
                    override var method: String? = "POST"
                    override var body: dynamic = formData
                    override var headers: dynamic = json("Accept" to "application/json")

                }).then {
                when (it.ok) {
                    true -> {
                        observer.next(Resource.success(State.SUCCESS))
                    }
                }

            }.catch { error: Throwable ->
                if (error is SheasyError)
                    observer.error(error)
                observer.complete()

            }

            null
        }

    }

    override fun getFiles(folderPath: String): Observable<List<FileResponse>> {
        return download(networkSettings.getFilesUrl(folderPath))
    }

    override fun getShared(): Observable<List<FileResponse>> {
        return download(networkSettings.getSharedFoldersUrl())
    }


    override fun getApps(): Observable<List<App>> {
        return download(networkSettings.getAppsUrl())
    }

    private inline fun <reified T> download(path: String): Observable<List<T>> {
        return Observable { observer ->


            Axios.get<Response<Array<T>>>(path, jsObject {
                timeout = 10000
            }).then { result ->
                when (result.data.status) {
                    "SUCCESS" -> {
                        result.data.data?.let {
                            observer.next(it.toMutableList())
                        }

                    }

                    "NotAuthorizedError" -> observer.error(SheasyError.NotAuthorizedError())
                    "ERROR" -> {
                        when (result.data.message) {
                            SheasyError.NoSharedFoldersError().message -> {
                                observer.error(SheasyError.NoSharedFoldersError())

                            }
                            else -> observer.error(SheasyError.UNKNOWNERROR())

                        }
                    }
                    else -> observer.error(SheasyError.UNKNOWNERROR())
                }
                observer.complete()

            }.catch { error: Throwable ->
                observer.error(error)
                observer.complete()

            }



            null
        }


    }
}