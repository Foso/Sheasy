package de.jensklingenberg.sheasy.web.ui.about


import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.model.Status
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.model.render
import react.*

interface AboutState : RState {
    var itemsList: List<SourceItem>
    var status: Status
}


class AboutView : RComponent<RProps, AboutState>(), AboutContract.View {


    private val presenter: AboutContract.Presenter = AboutPresenter(this)


    /****************************************** React Lifecycle methods  */

    override fun AboutState.init() {
        itemsList = emptyList()
    }

    override fun componentDidMount() {
        presenter.componentDidMount()
    }

    override fun RBuilder.render() {
        state.itemsList.render(this)
    }

    /****************************************** Presenter methods  */
    override fun setData(items: List<SourceItem>) {
        setState {
            status = Status.SUCCESS
            itemsList = items
        }
    }

    override fun showError(error: SheasyError) {}

}


fun RBuilder.about() = child(AboutView::class) {}

