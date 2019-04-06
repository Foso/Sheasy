package de.jensklingenberg.sheasy.web.ui.about

import components.materialui.icons.HistoryIcon
import de.jensklingenberg.sheasy.web.components.materialui.icons.CodeIcon
import de.jensklingenberg.sheasy.web.components.materialui.icons.InfoOutlinedIcon
import de.jensklingenberg.sheasy.web.model.ButtonItem
import de.jensklingenberg.sheasy.web.ui.common.ButtonSourceItem
import de.jensklingenberg.sheasy.web.model.LinkItem
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.web.ui.common.LinkSourceItem
import de.jensklingenberg.sheasy.web.ui.common.StringSourceItem


class AboutPresenter(val view: AboutContract.View) : AboutContract.Presenter {


    override fun onItemClicked(payload: Any) {}

    override fun componentDidMount() {
        view.setData(
            listOf(

               GenericListHeaderSourceItem(StringSourceItem("Info")),
                AboutSourceItem(AboutItem("Sheasy", "v0.1", InfoOutlinedIcon)),
                AboutSourceItem(AboutItem("Changelog","v0.1", HistoryIcon)),

                GenericListHeaderSourceItem(StringSourceItem("License")),
                AboutSourceItem(AboutItem("License", "Sheasy is licensed under Apache License 2.0", CodeIcon),{}),
                LinkSourceItem(LinkItem("GIT PAGE", ApiEndPoint.repoSite, "_blank")),
                        ButtonSourceItem(ButtonItem("Download Sheasy Apk"), this)

            )
        )

    }

    override fun componentWillUnmount() {}

}