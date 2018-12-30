package data.repository

import de.jensklingenberg.model.App
import de.jensklingenberg.react.network.API
import de.jensklingenberg.react.data.AppsDataSource


class AppsRepository(val api: API) : AppsDataSource {

    override fun getApps(onSuccess: (List<App>) -> Unit, onError: (de.jensklingenberg.model.Error) -> Unit) {
        api.getApps(onSuccess, onError)
    }

}