package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.model.Response
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import kodando.rxjs.Observable
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

    override fun uploadFile(file: File,folderPath: String): Observable<Resource<State>> {
        return Observable { observer ->
            val formData = FormData()
            formData.append(file.name, file, file.name)


            window.fetch(
                networkPreferences.baseurl + ApiEndPoint.shared + "?upload="+folderPath,
                object : RequestInit {
                    override var method: String? = "POST"
                    override var body: dynamic = formData
                    override var headers: dynamic = json("Accept" to "application/json")

                }).then {
                when (it.ok) {
                    true -> {
                        observer.next(Resource.success(State.SUCCESS))
                    }

                    else -> {
                        observer.error(Error.NetworkError())
                    }
                }

            }

            null
        }

    }

    override fun getFiles(folderPath: String): Observable<List<FileResponse>> {
        return download(ApiEndPoint.getFiles(folderPath))
    }

    override fun getShared(): Observable<List<FileResponse>> {
        return download(networkPreferences.baseurl + ApiEndPoint.shared)
    }


    override fun getApps(): Observable<List<App>> {
        return download(networkPreferences.baseurl + ApiEndPoint.apps)
    }

    /* private inline fun <reified T> download(path: String, calli: ResponseCallback<List<T>>) {
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

     */

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

                    "NotAuthorizedError" -> observer.error(Error.NotAuthorizedError())
                    "ERROR" -> {
                        when (result.data.message) {
                            Error.NoSharedFoldersError().message -> {
                                observer.error(Error.NoSharedFoldersError())

                            }
                            else -> observer.error(Error.UNKNOWNERROR())

                        }
                    }
                    else -> observer.error(Error.UNKNOWNERROR())
                }
                observer.complete()

            }.catch { error: Throwable ->
                observer.error(Error.NetworkError())
                observer.complete()

            }



            null
        }


    }
}