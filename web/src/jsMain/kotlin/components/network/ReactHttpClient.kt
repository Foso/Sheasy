package components.network

import de.jensklingenberg.model.App
import de.jensklingenberg.model.Error
import de.jensklingenberg.model.response.Response
import de.jensklingenberg.react.network.API
import kotlinext.js.jsObject

class ReactHttpClient(val baseurl: String) : API {

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

        onError(Error.NETWORK_ERROR)


    }
}