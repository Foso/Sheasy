package de.jensklingenberg.sheasy.web.ui.common

import components.materialui.Drawer
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import components.materialui.ListItemText
import de.jensklingenberg.sheasy.web.model.DrawerItems
import react.RBuilder
import  de.jensklingenberg.sheasy.web.components.materialui.List

class Drawer{

companion object {
     fun setupDrawer(
         rBuilder: RBuilder,
         drawerItems: Array<DrawerItems>,
         showDrawer: Boolean,
         test :()->Unit

    ) {
        rBuilder.run {
            Drawer {
                attrs {
                    open = showDrawer
                    onClose =  {test()}

                }

                drawerItems
                    .forEach {
                        List {
                            attrs {
                                component = "nav"
                            }
                            ListItem {
                                attrs {
                                    href = it.destination
                                    component = "a"
                                    divider = true
                                    styleProps(textAlign = "center")
                                    onClick={test()}
                                }

                                ListItemText {
                                    attrs {
                                        this.primary = it.title
                                    }
                                }
                            }
                        }
                    }
            }
        }

    }
}


}