package de.jensklingenberg.sheasy.web.ui.home

import de.jensklingenberg.sheasy.web.model.DrawerItems
import de.jensklingenberg.sheasy.web.model.HomeItem

class HomePresenter(val view :HomeContract.View) : HomeContract.Presenter{

    override fun componentDidMount() {

        DrawerItems
            .values()
            .filterNot {  it== DrawerItems.HOME }
            .map {
                HomeEntrySourceItem(HomeItem(it.title,it.destination))
            }.run { view.setData(this) }

    }

    override fun componentWillUnmount() {}


}