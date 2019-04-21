package de.jensklingenberg.sheasy.web.ui.common

import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.model.ButtonItem
import de.jensklingenberg.sheasy.web.model.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.SourceItem
import kotlinx.html.js.onClickFunction
import network.SharedNetworkSettings
import react.RBuilder
import react.dom.button
import kotlin.browser.window

class ButtonSourceItem(val buttonItem: ButtonItem, var onEntryClickListener: OnEntryClickListener? = null) : SourceItem() {

    override fun render(rBuilder: RBuilder) {
        with(rBuilder) {

                button {
                    attrs {
                        text(buttonItem.title)
                        onClickFunction = {
                            window.location.href =
                                SharedNetworkSettings(
                                    NetworkPreferences().baseurl).appDownloadUrl("de.jensklingenberg.sheasy")
                            onEntryClickListener?.onItemClicked("TEST")
                        }
                    }
                }
            }



    }

}