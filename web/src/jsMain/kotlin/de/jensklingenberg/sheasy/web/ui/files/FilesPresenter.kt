package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.model.StringRes
import kodando.rxjs.subscribeBy
import org.w3c.dom.events.Event
import org.w3c.files.File

class FilesPresenter(val view: FilesContract.View, val fileDataSource: FileDataSource) :
    FilesContract.Presenter {


    val defaultPath = "/"
    var folderPath = defaultPath
    var filesResult = listOf<FileResponse>()

    /****************************************** React Lifecycle methods  */

    override fun componentWillUnmount() {}

    override fun componentDidMount() {}

    /****************************************** Presenter methods  */
    override fun navigateUp() {
        folderPath = folderPath.substringBeforeLast("/", "")
        getFiles()
    }

    override fun setPath(path: String) {
        this.folderPath = path
        getFiles()
    }

    override fun getFiles() {

        fileDataSource.getFiles(folderPath).subscribeBy(
            next = { data ->
                filesResult = data

                filesResult.map { respo ->
                    FileSourceItem(respo,
                        { setPath(respo.path) },
                        { event: Event -> handleClickListItem(event, respo) })
                }.run {
                    if (this.isEmpty()) {

                        view.setData(
                            listOf(
                                FileSourceItem(FileResponse("No Files", ""))
                            )
                        )

                    } else {
                        view.setData(this)

                    }
                }
            }, error = {
                if (it is SheasyError) {
                    view.showError(it)

                }
            }
        )
    }

    override fun onSearch(query: String) {
        filesResult
            .filter { item ->
                item.name.contains(query, true)
            }
            .map { respo ->
                FileSourceItem(respo,
                    { setPath(respo.path) },
                    { event: Event -> handleClickListItem(event, respo) })
            }
            .run(view::setData)
    }

    override fun getShared() {

        fileDataSource.getShared().subscribeBy(
            next = { data ->
                filesResult = data
                filesResult.map { respo ->
                    FileSourceItem(respo,
                        { setPath(respo.path) },
                        { event: Event -> handleClickListItem(event, respo) })
                }.run {
                    view.setData(this)
                }
            }, error = {
                if (it is SheasyError) {
                    view.showError(it)

                }
            }
        )

    }

    fun setContextMenuVisibility(visibility: Boolean) {

        view.setContextMenuVisibility(visibility)
    }


    override fun uploadFile(file: File) {
        var fold = folderPath
        if (fold.last().toString() != "/") {
            fold = "$folderPath/"
        }

        fileDataSource.uploadFile(file, fold).subscribeBy(
            next = { data ->
                view.showSnackBar(StringRes.MESSAGE_SUCCESS)
                getFiles()
            }, error = {
                if (it is SheasyError) {
                    view.showError(it)

                }
            }
        )


    }

    override fun getFile(fileResponse: FileResponse?) {
        fileResponse?.let {
            fileDataSource.downloadFile(fileResponse)
        }

    }

    private fun handleClickListItem(event: Event, fileResponse: FileResponse) {
        view.handleClickListItem(event, fileResponse)
    }
}