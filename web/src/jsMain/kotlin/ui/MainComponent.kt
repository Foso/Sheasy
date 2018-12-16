package ui


import components.materialui.List
import components.materialui.ListItem
import components.materialui.ListItemText
import components.reactstrap.Container
import data.DrawerItems
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class App : RComponent<RProps, RState>() {


    override fun RBuilder.render() {
        toolbar()

        Container {
            DrawerItems
                .values()
                .forEach {
                    List {

                        ListItem {
                            ListItemText {
                                attrs {
                                    this.primary = it.title
                                }
                            }
                            attrs {
                                href = it.destination
                                component = "a"
                            }
                        }


                    }

                }

        }


    }
}

fun RBuilder.app() = child(App::class) {}




