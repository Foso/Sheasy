package de.jensklingenberg.sheasy.web.ui.share

import de.jensklingenberg.sheasy.web.data.EventDataSource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.repository.EventRepository
import kodando.rxjs.subscribeBy

class SharePresenter(val view: ShareContract.View) : ShareContract.Presenter {

    val eventDataSource: EventDataSource = EventRepository()

    /****************************************** React Lifecycle methods  */

    var viewIsUnmounted = false


    override fun componentDidMount() {
        view.setConnectedMessage("Connected to Server: " + NetworkPreferences().hostname)

        //  myWebSocket = MyWebSocket(shareWebSocketURL, this)
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