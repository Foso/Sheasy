package de.jensklingenberg.sheasy.ui.files

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.SheasyPrefDataSource
import de.jensklingenberg.sheasy.data.file.FileDataSource
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import model.FileResponse
import javax.inject.Inject


class FilesViewModel : ViewModel() {

    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    var filePath = ""


    var files: MutableLiveData<List<FileResponse>> = MutableLiveData()

    init {
        initializeDagger()
        filePath = sheasyPrefDataSource.defaultPath
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    fun loadFiles() {
        fileDataSource
            .getFiles(filePath)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { files.postValue(it) })
    }

    fun folderUp() {
        filePath = filePath.replaceAfterLast("/", "")
        loadFiles()
    }


}
