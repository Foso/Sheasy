package ui.files

import components.materialui.ListItem
import components.materialui.ListItemText
import kotlinx.html.js.onClickFunction
import model.Error
import model.FileResponse
import react.*
import react.dom.button
import ui.common.styleProps
import ui.common.toolbar


interface FilesState : RState {
    var filesList: List<FileResponse>
}


class FileView : RComponent<RProps, FilesState>(), FilesContract.View {

    val presenter = FilesPresenter(this)

    override fun FilesState.init() {
        filesList = emptyList()
    }

    override fun componentDidMount() {
        presenter.getFiles()
    }

    override fun RBuilder.render() {
        toolbar()

        button {
            attrs {
                text("Back")
                onClickFunction = {
                    presenter.navigateUp()
                }
            }
        }
        state.filesList
            .forEach {file->
                components.materialui.List {
                    attrs {
                        component = "nav"
                    }
                    ListItem {
                        attrs {
                           // href = presenter.getFiles(it.path)
                            component = "a"
                            divider = true
                            styleProps(textAlign = "left")
                            onClick={presenter.setPath(file.path)}
                        }

                        ListItemText {
                            attrs {
                                this.primary = file.name
                            }
                        }
                    }
                }
            }
    }

    override fun setData(filesResult: List<FileResponse>) {
        setState {
            filesList = filesResult

        }
    }

    override fun showError(error: Error) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
