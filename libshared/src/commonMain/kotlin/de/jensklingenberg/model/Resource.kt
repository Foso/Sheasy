package de.jensklingenberg.model



enum class Status(val title:String) {
      SUCCESS("SUCCESS"), ERROR("ERROR"), LOADING("LOADING")
}


data class Resource<T>(val status: String, val data: T?, val message: String?) {
    val onErrorStub: () -> Unit = {}
     val onSuccessStub: (T) -> Unit = {}
     val onLoadingStub: () -> Unit = {}


    companion object {

        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS.title, data, "")

        fun <T> error(msg: String, data: T?): Resource<T> = Resource(Status.ERROR.title, data, msg)

        fun <T> loading(msg: String): Resource<T> = Resource(Status.LOADING.title, null, msg)


    }



}
fun <T> Resource<T>.checkState(
    onSuccess: (T) -> Unit = this.onSuccessStub,
    onLoading: () -> Unit = this.onLoadingStub,
    onError: () -> Unit = this.onErrorStub
) {

    when (status) {
        Status.SUCCESS.title -> onSuccess(this.data!!)
        Status.ERROR.title -> onError()
        Status.LOADING.title -> onLoading()
    }
}