package de.jensklingenberg.react.network

import de.jensklingenberg.model.App


interface API {
    // fun<T> getApps():T
    fun getApps(onSuccess: (List<App>) -> Unit, onError: (de.jensklingenberg.model.Error) -> Unit)
}
