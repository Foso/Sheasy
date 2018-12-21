package model
data class DeviceResponse(
    val manufacturer: String,
    val model: String,
    val busySpace: Int,
    val totalSpace: Int,
    val freeSpace: Int,
    val androidVersion: String
)

data class FileResponse(val name: String, val path: String)
data class AppResponse(val name: String, val packageName: String, val installTime: String)

 class Respo{
companion object {
   val SUCCESS="SUCCESS"
    val LOADING="LOADING"
    val ERROR = "ERROR"

}


}




data class Response<T>(val status: String, val data: T?, val message: String?) {
    private val onErrorStub: () -> Unit = {}
    private val onSuccessStub: (T) -> Unit = {}
    private val onLoadingStub: () -> Unit = {}


    companion object {

        fun <T> success(data: T): Response<T> = Response(Respo.SUCCESS, data, "")

        fun <T> error(msg: String, data: T?): Response<T> = Response(Respo.ERROR, data, msg)

        fun <T> loading(msg: String): Response<T> = Response(Respo.LOADING, null, msg)
    }

    fun checkState(
        onSuccess: (T) -> Unit = onSuccessStub,
        onLoading: () -> Unit = onLoadingStub,
        onError: () -> Unit = onErrorStub
    ) {

        when (status) {
            Respo.SUCCESS -> onSuccess(this.data!!)
            Respo.ERROR -> onError()
            Respo.LOADING -> onLoading()
        }
    }


}
