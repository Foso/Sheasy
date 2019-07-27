package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.web.data.EventDataSource
import de.jensklingenberg.sheasy.web.data.repository.EventRepository
import kodando.rxjs.subscribeBy

class NotificationPresenter(private val view: NotificationContract.View) : NotificationContract.Presenter {

    private val eventDataSource: EventDataSource = EventRepository()


    /****************************************** React Lifecycle methods  */


    override fun componentDidMount() {
        eventDataSource
            .observNotifcations()
            .subscribeBy(next = { shareItem ->
                view.showNotification(shareItem)
            }, error = {


            }, complete = {


            })

    }

    override fun componentWillUnmount() {


    }


}