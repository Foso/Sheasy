package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject


class FilesPresenter(val view: FilesContract.View) : FilesContract.Presenter {



    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var shareUseCase: ShareUseCase


    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override var fileResponse1 = FileResponse("","")

    var shared: List<BaseDataSourceItem<*>> = emptyList()

    var files: List<BaseDataSourceItem<*>> = emptyList()

    init {
        initializeDagger()
        fileResponse1= FileResponse(File(sheasyPrefDataSource.defaultPath).name,sheasyPrefDataSource.defaultPath)
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onCreate() {

        loadFiles()

    }

    override fun loadFiles() {

        //TODO: Combine the observables

        sheasyPrefDataSource
            .observeSharedFolders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { fileList ->

                val list = mutableListOf<BaseDataSourceItem<*>>()

                shared = list.apply {
                    if (fileList.isNotEmpty()) {
                        add(
                            GenericListHeaderSourceItem(
                                "Shared Folders"
                            )
                        )
                        addAll(fileList.map {
                            SharedFolderSourceItem(File(it.path), this@FilesPresenter)
                        })
                    }

                }

                view.setData(shared + files)
            }.addTo(compositeDisposable)


        fileDataSource
            .observeFiles(fileResponse1.path)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = { fileList ->
                val list = mutableListOf<BaseDataSourceItem<*>>()

                files = list.apply {
                    if (fileList.isNotEmpty()) {
                        add(
                            GenericListHeaderSourceItem(
                                "Folders"
                            )
                        )
                        addAll(fileList.sortedBy { it.isFile }.map {
                            if (it.isFile) {
                                FileSourceItem(it, this@FilesPresenter)

                            } else {
                                FolderSourceItem(it, this@FilesPresenter)

                            }
                        })
                    }

                }

                view.setData(shared + files)

            }, onError = { view.showError(it) })
            .addTo(compositeDisposable)

    }


    override fun folderUp() {

        var oldFolderPath = fileResponse1.path
        var newPath = oldFolderPath.replaceAfterLast("/", "")
        fileResponse1 = FileResponse(File(newPath).name,newPath)
         loadFiles()
        view.updateFolderPathInfo(fileResponse1)

    }



    /****************************************** Listener methods  */

    override fun onItemClicked(item: FileResponse) {
        fileResponse1 = item
        loadFiles()
        view.updateFolderPathInfo(item)
    }



    override fun onPopupMenuClicked(fileResponse: FileResponse, id: Int) {
        when (id) {
            R.id.menu_share -> {
                shareUseCase.share(File(fileResponse.path))
            }
            R.id.menu_share_to_server -> {
                shareUseCase.hostFolder(fileResponse)
            }
            R.id.menu_share_link->{
                shareUseCase.shareDownloadLink(fileResponse)
            }
            R.id.menu_unhost_folder->{
                shareUseCase.removeHostFolder(fileResponse)
            }
        }

    }


    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun share(file: File) {
        shareUseCase.share(file)

    }

    override fun hostFolder(fileResponse: FileResponse) {
        shareUseCase.hostFolder(fileResponse)
    }

    override fun searchFile(fileName: String) {


    }

    override fun hostActiveFolder() {
       shareUseCase.hostFolder(fileResponse1)
    }


}