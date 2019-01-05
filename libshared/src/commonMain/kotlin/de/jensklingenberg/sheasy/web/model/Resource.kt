package de.jensklingenberg.sheasy.web.model



enum class Status(val title:String) {
      SUCCESS("SUCCESS"), ERROR("ERROR"), LOADING("LOADING")
}


data class Resource<T>(val status: String, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS.title, data, "")

        fun <T> error(msg: String, data: T?): Resource<T> = Resource(Status.ERROR.title, data, msg)

        fun <T> error(error: Error,msg: String, data: T?): Resource<T> = Resource(error.title, data, msg)


        fun <T> loading(msg: String): Resource<T> = Resource(Status.LOADING.title, null, msg)


    }



}
fun <T> Resource<T>.checkState(
    onSuccess: (T) -> Unit = {},
    onLoading: () -> Unit = {},
    onError: () -> Unit = {}
) {

    when (status) {
        Status.SUCCESS.title -> onSuccess(this.data!!)
        Status.ERROR.title -> onError()
        Status.LOADING.title -> onLoading()
    }
}