package de.jensklingenberg.sheasy.ui.files

import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.ui.common.toSourceitem
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import io.reactivex.Observable
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

    override var filePath = ""

    init {
        initializeDagger()
        filePath = sheasyPrefDataSource.defaultPath
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onCreate() {

        loadFiles()

    }

    override fun loadFiles() {

        sheasyPrefDataSource.sharedFoldersObs().subscribeBy(onNext = {

            view.setData(it.map { x->x.toSourceitem(this) }
            )
        }) .addTo(compositeDisposable)


        fileDataSource
            .getFiles(filePath)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = { files ->
                val tt = mutableListOf<BaseDataSourceItem<*>>()

                tt.add(
                    GenericListHeaderSourceItem(
                        "Shared Folders"
                    )
                )

                tt.addAll(


                    sheasyPrefDataSource.sharedFolders.map {
                        SharedFolderSourceItem(it)
                    }
                )

                tt.add(
                    GenericListHeaderSourceItem(
                        "Folders"
                    )
                )

                files
                    .sortedBy { it.isFile() }
                    .forEach { tt.add(it.toSourceitem(this)) }

                view.setData(tt)

            }, onError = { view.showError(it) })
            .addTo(compositeDisposable)

    }





    override fun folderUp() {
        filePath = filePath.replaceAfterLast("/", "")
        loadFiles()
        view.updateFolderPathInfo(filePath)

    }


    /****************************************** Listener methods  */
    override fun onItemClicked(payload: Any) {
        val item = payload as FileResponse
        filePath = item.path
        loadFiles()
        view.updateFolderPathInfo(item.path)
    }


    override fun onMoreButtonClicked(view: View, payload: Any) {
        val item = payload as? FileResponse

        this.view.showPopup(item, view)


    }


    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun share(file: File) {
        shareUseCase.share(file)

    }

    override fun hostFolder(item: FileResponse) {
        shareUseCase.hostFolder(item)

    }

    override fun searchFile(fileName: String) {
        fileDataSource
            .getFiles(filePath)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribeBy(onSuccess = { files ->
                files
                    .filter { it.name.contains(fileName, true) }
                    .sortedBy { it.isFile() }
                    .map { it.toSourceitem(this) }

                    .run {
                        // view.setData(this)
                    }
            }, onError = {})
            .addTo(compositeDisposable)

    }


}