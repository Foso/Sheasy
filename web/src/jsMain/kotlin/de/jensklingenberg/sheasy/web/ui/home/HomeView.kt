package de.jensklingenberg.sheasy.web.ui.home

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.model.render
import react.*


interface HomeViewState : RState {
    var item: List<SourceItem>

}

class HomeView :  RComponent<RProps, HomeViewState>(), HomeContract.View {
    var presenter: HomeContract.Presenter = HomePresenter(this)

    /****************************************** React Lifecycle methods  */

    override fun HomeViewState.init() {
        item = emptyList()

    }

    override fun componentDidMount() {
        presenter.componentDidMount()
    }


    override fun RBuilder.render() {
        state.item.render(this)

    }

    /****************************************** Presenter methods  */


    override fun setData(items: List<SourceItem>) {
        setState {
            this.item = items
        }
    }


}


fun RBuilder.home() = child(HomeView::class) {}




