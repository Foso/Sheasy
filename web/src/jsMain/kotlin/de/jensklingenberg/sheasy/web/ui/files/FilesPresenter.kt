package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.StringRes
import de.jensklingenberg.sheasy.web.model.response.Resource
import de.jensklingenberg.sheasy.web.network.ResponseCallback
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
        folderPath = folderPath.replaceAfterLast("/", "")
        getFiles()
    }

    override fun setPath(path: String) {
        this.folderPath = path
        getFiles()
    }

    override fun getFiles() {
        fileDataSource.getFiles(
            folderPath = folderPath, callback = object : ResponseCallback<List<FileResponse>> {
                override fun onSuccess(data: List<FileResponse>) {
                    filesResult = data
                    view.setData(data)
                }

                override fun onError(error: Error) {
                    view.showError(error)
                }

            }


        )
    }

    override fun getShared() {
        fileDataSource.getShared(
            callback = object : ResponseCallback<List<FileResponse>> {
                override fun onSuccess(data: List<FileResponse>) {
                    filesResult = data
                    view.setData(data)
                }

                override fun onError(error: Error) {
                    view.showError(error)
                }

            }


        )
    }

    override fun uploadFile(file: File) {
        fileDataSource.uploadFile(
            file, callback = object : ResponseCallback<Resource<State>> {
                override fun onSuccess(data: Resource<State>) {
                    view.showSnackBar(StringRes.MESSAGE_SUCCESS)
                }

                override fun onError(error: Error) {
                    view.showError(error)

                }


            }


        )

    }
}