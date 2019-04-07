package de.jensklingenberg.sheasy.web.ui.connection

import de.jensklingenberg.sheasy.web.model.DrawerItems
import de.jensklingenberg.sheasy.web.ui.home.HomeEntrySourceItem
import de.jensklingenberg.sheasy.web.ui.home.HomeItem

class ConnectionPresenter(val view : ConnectionContract.View) : ConnectionContract.Presenter {



    override fun componentDidMount() {

        DrawerItems
            .values()
            .filterNot {  it== DrawerItems.HOME }
            .map {
                HomeEntrySourceItem(HomeItem(it.title,it.destination))
            }.run { view.setData(this) }


    }

    override fun componentWillUnmount() {

    }


}