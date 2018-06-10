package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import de.jensklingenberg.sheasy.model.ConnectionInfo
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.api.ChangeableBaseUrlInterceptor
import de.jensklingenberg.sheasy.network.api.SheasyAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class NetworkViewModel(
    val application2: Application,
    val changeableBaseUrlInterceptor: ChangeableBaseUrlInterceptor,
    val sheasyAPI: SheasyAPI
) : AndroidViewModel(application2) {


    var devices: MutableLiveData<Resource<List<ConnectionInfo>>> = MutableLiveData()


    fun findDevices(application: Application) {
        changeableBaseUrlInterceptor.setHost("http://192.168.2.35:8766/")

        sheasyAPI.connect().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Response<List<ConnectionInfo>>>() {
                override fun onSuccess(t: Response<List<ConnectionInfo>>) {
                    Log.d("dd", t.message())
                    devices.value = Resource.success(t.body() ?: emptyList())
                }

                override fun onError(e: Throwable) {
                    Log.d("dd", e.message)
                }

            })


    }


}