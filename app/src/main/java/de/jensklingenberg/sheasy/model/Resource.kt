package de.jensklingenberg.sheasy.model

import de.jensklingenberg.sheasy.R

enum class Status {
    SUCCESS, ERROR, LOADING
}


data class Resource<T>(val status: Status, val data: T?, val message: Int?) {
    private val onErrorStub: () -> Unit = {}
    private val onSuccessStub: (T) -> Unit = {}
    private val onLoadingStub: () -> Unit = {}


    companion object {

        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, R.string.Success)

        fun <T> error(msg: Int, data: T?): Resource<T> = Resource(Status.ERROR, data, msg)

        fun <T> loading(msg: Int): Resource<T> = Resource(Status.LOADING, null, msg)
    }


    fun checkState(
        onSuccess: (T) -> Unit = onSuccessStub,
        onLoading: () -> Unit = onLoadingStub,
        onError: () -> Unit = onErrorStub
    ) {

        when (status) {
            Status.SUCCESS -> onSuccess(this.data!!)
            Status.ERROR -> onError()
            Status.LOADING -> onLoading()
        }
    }
}
