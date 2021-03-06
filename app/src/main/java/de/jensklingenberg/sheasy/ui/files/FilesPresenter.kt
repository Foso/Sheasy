package de.jensklingenberg.sheasy.ui.files

import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.ui.common.NoOrEmptyContentItem
import de.jensklingenberg.sheasy.ui.common.NoOrEmptyContentSourceItem
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
    lateinit var shareUseCaseProvider: ShareUseCase


    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override var fileResponse1 = FileResponse("", "")

    private var shared: List<BaseDataSourceItem<*>> = emptyList()

    private var files: List<BaseDataSourceItem<*>> = emptyList()

    init {
        initializeDagger()
        fileResponse1 = FileResponse(File(sheasyPrefDataSource.defaultPath).name, sheasyPrefDataSource.defaultPath)
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
                                "Shared Folders/Files"
                            )
                        )
                        addAll(fileList.map { fileResponse ->
                            SharedFolderSourceItem(fileResponse, fileResponse.isFile(), this@FilesPresenter)
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
                        addAll(fileList
                            .sortedBy { it.isFile }
                            .map { file ->
                                if (file.isFile) {
                                    FolderSourceItem(
                                        file,
                                        !file.isFile,
                                        R.drawable.ic_insert_drive_file_grey_700_24dp,
                                        { _, _ -> }
                                    ) { view, fileResponse ->
                                        setupContextMenu(
                                            view,
                                            fileResponse,
                                            R.menu.files_actions
                                        )
                                    }
                                } else {
                                    FolderSourceItem(
                                        file,
                                        !file.isFile,
                                        R.drawable.ic_folder_grey_700_24dp,
                                        { view, file -> onEntryClicked(view, file) }
                                    ) { view, fileResponse ->
                                        setupContextMenu(
                                            view,
                                            fileResponse,
                                            R.menu.folders_actions
                                        )
                                    }

                                }
                            })
                    }

                }

                if (files.isEmpty()) {

                    (files as MutableList<BaseDataSourceItem<*>>).add(
                        GenericListHeaderSourceItem(
                            "Folders"
                        )
                    )


                    (files as MutableList<BaseDataSourceItem<*>>).add(
                        NoOrEmptyContentSourceItem(
                            NoOrEmptyContentItem(
                                "No Files",
                                R.drawable.ic_insert_drive_file_grey_700_24dp
                            )
                        )
                    )

                }

                view.setData(shared + files)

            }, onError = { view.showError(it) })
            .addTo(compositeDisposable)

    }


    override fun folderUp() {

        var oldFolderPath = fileResponse1.path
        var newPath = oldFolderPath.substringBeforeLast("/")
        fileResponse1 = FileResponse(File(newPath).name, newPath)
        loadFiles()
        view.updateFolderPathInfo(fileResponse1)

    }


    /****************************************** Listener methods  */

    override fun onItemClicked(item: FileResponse) {
        fileResponse1 = item
        loadFiles()
        view.updateFolderPathInfo(item)
    }

    fun onEntryClicked(view: View, fileResponse: FileResponse) {
        onItemClicked(fileResponse)

    }

    fun onPopupMenuClicked(fileResponse: FileResponse, id: Int) {
        when (id) {
            R.id.menu_share -> {
                shareUseCaseProvider.share(File(fileResponse.path))
            }
            R.id.menu_share_to_server -> {
                shareUseCaseProvider.hostFolder(fileResponse)
            }
            R.id.menu_share_link -> {
                shareUseCaseProvider.shareFolderLink(fileResponse)
            }
            R.id.menu_unhost_folder -> {
                shareUseCaseProvider.removeHostFolder(fileResponse)
            }

        }

    }

    override fun onPopupMenuClicked(view: View, fileResponse: FileResponse) {
        PopupMenu(view.context, view)
            .apply {
                menuInflater
                    .inflate(R.menu.shared_folder_actions, menu)
            }
            .also {
                it.itemClicks()
                    .doOnNext { menuItem ->
                        onPopupMenuClicked(
                            fileResponse,
                            menuItem.itemId
                        )
                    }.subscribe()
            }.show()

    }


    private fun setupContextMenu(
        it: View,
        fileResponse: FileResponse,
        menuId: Int
    ) {
        PopupMenu(it.context, it)
            .apply {
                menuInflater
                    .inflate(menuId, menu)
            }
            .also {
                it.itemClicks()
                    .doOnNext { menuItem ->

                        onPopupMenuClicked(
                            fileResponse,
                            menuItem.itemId
                        )

                    }.subscribe()
            }.show()
    }

    override fun share(file: File) {
        shareUseCaseProvider.share(file)

    }

    override fun hostFolder(fileResponse: FileResponse) {
        shareUseCaseProvider.hostFolder(fileResponse)
    }

    override fun searchFile(fileName: String) {


    }


    override fun hostActiveFolder() {
        shareUseCaseProvider.hostFolder(fileResponse1)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun removeAllHostedFolders() {
        shareUseCaseProvider.removeAllHostedFolders()
    }

}