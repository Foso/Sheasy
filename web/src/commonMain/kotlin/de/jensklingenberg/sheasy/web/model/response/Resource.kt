package de.jensklingenberg.sheasy.web.model.response


enum class Status(val title:String) {
    SUCCESS("SUCCESS"), ERROR("ERROR"), LOADING("LOADING")
}


data class Resource<T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, "")
        fun <T> error(msg: String, data: T?): Resource<T> = Resource(Status.ERROR, data, msg)
        fun <T> loading(msg: String): Resource<T> = Resource(Status.LOADING, null, msg)
    }
}
fun <T> Resource<T>.checkState(
    onSuccess: (T) -> Unit = {},
    onLoading: () -> Unit = {},
    onError: () -> Unit = {}
) {

    when (status) {
        Status.SUCCESS -> onSuccess(this.data!!)
        Status.ERROR -> onError()
        Status.LOADING -> onLoading()
    }
}


