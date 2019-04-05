package de.jensklingenberg.sheasy.ui.share

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.event.EventRepository
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.EventCategory
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import de.jensklingenberg.sheasy.web.model.Device
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SharePresenter(val view: ShareContract.View) : ShareContract.Presenter {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var eventDataSource: EventDataSource

    @Inject
    lateinit var server: Server


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)



    override fun onCreate() {
        sheasyPrefDataSource.devicesRepository.authorizedDevices.apply {
            if (isEmpty()) {
                add(Device("No connected device"))
            } else {
                remove(Device("No connected device"))
            }
        }.map {
            ShareItemSourceItem(it)
        }.run {
           // view.setData(this)

        }


        compositeDisposable.add(
            eventDataSource.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                onNext = {
                    view.setData(it.map { it.toSourceItem() })
                }
            )
        )

        eventDataSource.addEvent(Event(EventCategory.CONNECTION,"555"))


    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun sendMessage(s: String) {
        server.sendData(Server.DataDestination.SHARE,s)

    }


}
