package de.jensklingenberg.react.ui.files

import kotlinext.js.jsObject
import de.jensklingenberg.model.Error
import de.jensklingenberg.model.response.Response
import de.jensklingenberg.model.response.FileResponse
import components.network.Axios
import components.network.ApiEndPoint

class FilesPresenter(val view: FilesContract.View) : FilesContract.Presenter {


    val defaultPath = "/"
    var path = defaultPath
    var filesResult = listOf<FileResponse>()

    /****************************************** React Lifecycle methods  */
    override fun componentWillUnmount() {}

    override fun componentDidMount() {}

    /****************************************** Presenter methods  */
    override fun navigateUp() {
        path = path.replaceAfterLast("/", "")
        getFiles()
    }

    override fun setPath(path: String) {
        this.path = path
        getFiles()
    }

    override fun getFiles() {
        Axios.get<Response<Array<FileResponse>>>(ApiEndPoint.getFiles(path), jsObject {
            timeout = 10000
        }).then { result ->

            if ("SUCCESS" == "SUCCESS") {
                filesResult = result.data.data!!.toMutableList().sortedBy { it.name }
                view.setData(filesResult)

            } else {
                view.showError(Error.NOT_AUTHORIZED)
            }
        }.catch { error ->
            view.showError(Error.NETWORK_ERROR)
            console.log(error)
        }
    }

}