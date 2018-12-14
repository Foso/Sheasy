package ui.apps

import components.reactstrap.*
import data.StringResource
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import react.*
import react.dom.div

interface AppsProps : RProps {
    var startFrom: Boolean
}

interface AppsState : RState {
    var modalIsOpen: Boolean
}

class Apps(props: AppsProps) : RComponent<AppsProps, AppsState>(props) {

    override fun AppsState.init() {
        modalIsOpen = true

    }

    private fun handleChange() {
        setState {
            modalIsOpen = !modalIsOpen
        }

    }

    override fun RBuilder.render() {
        div {
            +"APPS"
            attrs {
                onClickFunction = { handleChange() }

            }
        }
        Modal {
            attrs {
                isOpen = this@Apps.state.modalIsOpen
                size = "lg"
            }
            ModalHeader {
                attrs {
                    toggle = {
                        handleChange()
                    }
                }

                div {
                    +StringResource.APPS_OVERVIEW_TABLE_HEADER

                    attrs {
                        style = kotlinext.js.js {
                            textAlign = "center"
                        }

                    }
                }


            }
            ModalBody {
                appsView()
            }
            ModalFooter {

                Container {
                    Row {
                        Col {
                            components.materialui.Button {
                                +"Do Something"
                                attrs {
                                    variant = "contained"
                                }
                            }
                        }

                        Col {

                            components.materialui.Button {
                                +"Cancel"
                                attrs {
                                    variant = "contained"
                                }
                            }
                        }
                    }
                }


            }

        }

    }

}

fun RBuilder.apps() = child(Apps::class) {

}
