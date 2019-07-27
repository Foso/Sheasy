package de.jensklingenberg.sheasy.web.ui.share

import Application
import de.jensklingenberg.sheasy.web.data.EventDataSource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import kodando.rxjs.subscribeBy

class SharePresenter(val view: ShareContract.View) : ShareContract.Presenter {
    private val eventDataSource: EventDataSource = Application.eventDataSource

    /****************************************** React Lifecycle methods  */

    var viewIsUnmounted = false


    override fun componentDidMount() {
        view.setConnectedMessage("Connected to Server: " + NetworkPreferences().hostname)

        eventDataSource
            .observeEvents()
            .subscribeBy(next = { shareItem ->
                view.setData(shareItem)
            }, error = {
                view.setConnectedMessage("No connection")

            }, complete = {
                view.setConnectedMessage("No connection")

            })
    }

    override fun componentWillUnmount() {
        viewIsUnmounted = true
    }

    override fun send(message: String) {
        eventDataSource.sendMessage(message)

    }

    /****************************************** MyWebSocket methods  */


}