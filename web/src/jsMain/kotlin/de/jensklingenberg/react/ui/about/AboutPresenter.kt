package de.jensklingenberg.react.ui.about

import components.listview.ButtonItem
import components.listview.ButtonSourceItem
import components.listview.StringSourceItem

class AboutPresenter(val view:AboutContract.View):AboutContract.Presenter{


    override fun componentDidMount() {

        //"Sheasy v.0.0.1"))

        view.setData(listOf(
            StringSourceItem("Sheasy v.0.0.1"),
            ButtonSourceItem(ButtonItem("Download Sheasy Apk"))
        ))

    }

    override fun componentWillUnmount() {
    }

}