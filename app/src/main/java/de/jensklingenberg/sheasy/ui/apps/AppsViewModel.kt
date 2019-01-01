package de.jensklingenberg.sheasy.ui.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.file.FileDataSource
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import de.jensklingenberg.sheasy.web.model.AppInfo
import de.jensklingenberg.sheasy.web.model.Resource
import javax.inject.Inject


class AppsViewModel : ViewModel() {

    @Inject
    lateinit var fileDataSource: FileDataSource

    private var query: String = ""

    private val getApps = MutableLiveData<Resource<List<AppInfo>>>()

    /****************************************** Lifecycle methods  */

    init {
        initializeDagger()
        loadApps()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    fun searchApp(query: String): LiveData<Resource<List<AppInfo>>> {
        return Transformations.map(getApps) { app ->
            app.data!!
                .filter {
                    it.name.contains(query)
                }.run {
                    Resource.success(this)
                }
        }


    }

    private fun loadApps() {
        getApps.value = Resource.loading("loading")
        fileDataSource
            .getApps()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { getApps.postValue(Resource.success(it)) })
    }

    fun getApps(): LiveData<Resource<List<AppInfo>>> {
        loadApps()
        return getApps
    }


}
