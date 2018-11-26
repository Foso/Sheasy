package de.jensklingenberg.sheasy.ui.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AppsViewModel : ViewModel() {

    @Inject
    lateinit var fileDataSource: FileDataSource

    private var query: String = ""

    private val getApps = MutableLiveData<List<AppInfo>>().apply {
        value = emptyList()
    }

    init {
        initializeDagger()
        getApps.value = emptyList()
        loadApps()
    }

    private fun initializeDagger() = App.appComponent.inject(this)




    fun searchApp(query: String): LiveData<List<AppInfo>> {
        return Transformations.map(getApps) { app ->
            app
                .filter {
                    it.name.contains(query)
                }
        }


    }

    private fun loadApps() {

        fileDataSource
            .getApps()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { getApps.postValue(it) })
    }

    fun getApps(): LiveData<List<AppInfo>> {
        loadApps()
        return getApps
    }


}
