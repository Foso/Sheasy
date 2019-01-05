package de.jensklingenberg.sheasy.web.ui.common

import components.materialui.Drawer
import components.materialui.ListItem
import components.materialui.ListItemText
import de.jensklingenberg.sheasy.web.model.DrawerItems
import react.RBuilder

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
                    onClose =  {_->test()}
                }

                drawerItems
                    .forEach {
                        components.materialui.List {
                            attrs {
                                component = "nav"
                            }
                            ListItem {
                                attrs {
                                    href = it.destination
                                    component = "a"
                                    divider = true
                                    styleProps(textAlign = "center")
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