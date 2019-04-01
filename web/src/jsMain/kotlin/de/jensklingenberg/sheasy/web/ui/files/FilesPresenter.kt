package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.model.StringRes
import de.jensklingenberg.sheasy.web.network.ResponseCallback
import kodando.rxjs.subscribeBy
import org.w3c.files.File

class FilesPresenter(val view: FilesContract.View, val fileDataSource: FileDataSource) :
    FilesContract.Presenter {


    val defaultPath = "/"
    var folderPath = defaultPath
    var filesResult = listOf<FileResponse>()

    /****************************************** React Lifecycle methods  */

    init {

    }

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
                view.setData(data)
            }, error = {
                if (it is Error) {
                    view.showError(it)

                }
            }
        )
    }

    override fun onSearch(query: String) {
        filesResult
            .filter {
                it.name.contains(query, true)
            }
            .run(view::setData)
    }

    override fun getShared() {

        fileDataSource.getShared().subscribeBy(
            next = { data ->
                filesResult = data
                view.setData(data)
            }, error = {
                if (it is Error) {
                    view.showError(it)

                }
            }
        )

    }

    override fun uploadFile(file: File) {
        var fold = folderPath
        if(fold.last().toString()!="/"){
            fold= "$folderPath/"
        }

        fileDataSource.uploadFile(file,fold).subscribeBy(
            next = { data ->
                view.showSnackBar(StringRes.MESSAGE_SUCCESS)

            }, error = {
                if (it is Error) {
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
}