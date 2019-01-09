package de.jensklingenberg.sheasy.web.ui.about

import de.jensklingenberg.sheasy.web.KodeinInject
import de.jensklingenberg.sheasy.web.components.listview.ButtonItem
import de.jensklingenberg.sheasy.web.components.listview.ButtonSourceItem
import de.jensklingenberg.sheasy.web.components.listview.LinkItem
import de.jensklingenberg.sheasy.web.components.listview.LinkSourceItem
import de.jensklingenberg.sheasy.web.components.listview.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.StringSourceItem
import de.jensklingenberg.sheasy.web.network.ApiEndPoint


class AboutPresenter(val view:AboutContract.View):AboutContract.Presenter, OnEntryClickListener {

    @KodeinInject

    override fun onItemClicked(payload: Any) {}

    override fun componentDidMount() {
        view.setData(listOf(
            StringSourceItem("Sheasy v.0.0.1"),
            ButtonSourceItem(ButtonItem("Download Sheasy Apk"),this),
            LinkSourceItem(LinkItem("GIT PAGE", ApiEndPoint.repoSite,"_blank"))
        ))

    }

    override fun componentWillUnmount() {}

}