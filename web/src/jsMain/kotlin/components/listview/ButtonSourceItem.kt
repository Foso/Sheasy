package components.listview

import components.network.ApiEndPoint
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.dom.button
import react.dom.div
import kotlin.browser.window

class ButtonSourceItem(val buttonItem: ButtonItem,var onEntryClickListener: OnEntryClickListener? = null) : SourceItem() {

    override fun tterror(rBuilder: RBuilder) {
        with(rBuilder) {
            div {
                button {
                    attrs {
                        text(buttonItem.title)
                        onClickFunction = {
                            window.location.href =
                                ApiEndPoint.appDownloadUrl("de.jensklingenberg.sheasy")
                        }
                    }
                }
            }


        }

    }

}