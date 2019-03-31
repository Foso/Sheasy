package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.Error

interface ResponseCallback<T>{
    fun onSuccess(data:(T))
    fun onError(error: Error)
}

