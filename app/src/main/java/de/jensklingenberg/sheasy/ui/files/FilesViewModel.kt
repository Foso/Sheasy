package de.jensklingenberg.sheasy.ui.files

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import de.jensklingenberg.sheasy.web.model.FileResponse
import de.jensklingenberg.sheasy.web.model.Resource
import repository.SheasyPrefDataSource
import java.io.File
import javax.inject.Inject


class FilesViewModel : ViewModel() {

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

    fun loadFiles() {
        files.value=Resource.loading("loading")
        fileDataSource
            .getFiles(filePath)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { files.postValue(Resource.success(it)) },onError = {})
    }

    fun shareFile(file: File){
        shareUseCase.share(file)
    }



    fun folderUp() {
        filePath = filePath.replaceAfterLast("/", "")
        loadFiles()
    }


}
