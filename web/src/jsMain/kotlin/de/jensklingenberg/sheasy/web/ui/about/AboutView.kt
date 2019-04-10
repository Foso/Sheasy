package de.jensklingenberg.sheasy.web.ui.about


import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.model.Status
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.model.render
import de.jensklingenberg.sheasy.web.ui.common.BaseComponent
import react.RBuilder
import react.RProps
import react.RState
import react.setState

interface AboutState : RState {
    var itemsList: List<SourceItem>
    var status: Status
}


class AboutView : BaseComponent<RProps, AboutState>(), AboutContract.View {


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

    override fun showError(error: Error) {}

}


fun RBuilder.about() = child(AboutView::class) {}

