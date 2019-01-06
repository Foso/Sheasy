package de.jensklingenberg.sheasy.web.ui.about


import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.model.render
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.Status
import de.jensklingenberg.sheasy.web.ui.common.BaseComponent
import de.jensklingenberg.sheasy.web.ui.common.toolbar
import de.jensklingenberg.sheasy.web.usecase.NotificationOptions
import de.jensklingenberg.sheasy.web.usecase.NotificationUseCase
import react.RBuilder
import react.RProps
import react.RState
import react.setState

interface AboutState : RState {
    var itemsList: List<SourceItem>
    var status: Status
}


class AboutView : BaseComponent<RProps, AboutState>(), AboutContract.View {
    private val presenter: AboutPresenter = AboutPresenter(this)
    val notificationUseCase=NotificationUseCase()
    val notificationOptions= NotificationOptions(title = "Hall",subText = "uUUU",icon = "https://avatars3.githubusercontent.com/u/5015532?s=40&v=4",tag="dd")

    /****************************************** React Lifecycle methods  */

    override fun AboutState.init() {
        itemsList = emptyList()
    }

    override fun componentDidMount() {
        presenter.componentDidMount()
    }

    override fun RBuilder.render() {
        toolbar()
        state.itemsList.render(this)
       // notificationUseCase.showNotification(this,notificationOptions)
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


