package de.jensklingenberg.sheasy.web.network
interface ResponseCallback<T>{
    fun onSuccess(data:(T))
    fun onError(error: de.jensklingenberg.sheasy.web.model.Error)
}

