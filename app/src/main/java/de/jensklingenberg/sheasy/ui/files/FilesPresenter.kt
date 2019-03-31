package de.jensklingenberg.sheasy.ui.files

import androidx.lifecycle.MutableLiveData
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

interface FilesContract{
    interface Presenter{
        fun onCreate()
        fun loadFiles()
        fun folderUp()
    }
    interface View
}


class FilesPresenter(val view: FilesContract.View) : FilesContract.Presenter{


    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var shareUseCase: ShareUseCase

    var filePath = ""


    var files: MutableLiveData<Resource<List<FileResponse>>> = MutableLiveData()

    init {
        initializeDagger()
        filePath = sheasyPrefDataSource.defaultPath
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun loadFiles() {
        files.value= Resource.loading("loading")
        fileDataSource
            .getFiles(filePath)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { files.postValue(Resource.success(it)) },onError = {})
    }

    fun shareFile(file: File){
        shareUseCase.share(file)
    }



   override fun folderUp() {
        filePath = filePath.replaceAfterLast("/", "")
        loadFiles()
    }

    override fun onCreate() {


    }


}