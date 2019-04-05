package de.jensklingenberg.sheasy.model





data class Resource<T>(val status: String, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS.title, data, "")

        fun <T> error(msg: String, data: T?): Resource<T> = Resource(Status.ERROR.title, data, msg)

        fun <T> error(error: Error): Resource<T> = Resource(Status.ERROR.title, "empty" as T, error.message)


        fun <T> error(error: Error, msg: String, data: T?): Resource<T> = Resource(error.message, data, msg)


        fun <T> loading(msg: String): Resource<T> = Resource(Status.LOADING.title, null, msg)


    }



}
fun <T> Resource<T>.checkState(
    onSuccess: (Resource<T>) -> Unit = {},
    onLoading: () -> Unit = {},
    onError: (Resource<T>) -> Unit = {}
) {

    when (status) {
        Status.SUCCESS.title -> onSuccess(this)
        Status.ERROR.title -> onError(this)
        Status.LOADING.title -> onLoading()
    }
}