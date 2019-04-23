package de.jensklingenberg.sheasy.ui.share

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.model.EventCategory
import de.jensklingenberg.sheasy.model.MessageEvent
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import de.jensklingenberg.sheasy.ui.eventlog.EventSourceItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SharePresenter(val view: ShareContract.View) : ShareContract.Presenter {


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var eventDataSource: EventDataSource

    @Inject
    lateinit var server: Server

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCreate() {

        eventDataSource.getEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    view.setData(it
                        .filter { it is MessageEvent }
                        .map { MessageSourceItem(it as MessageEvent) })
                }
            ).also { compositeDisposable.add(it) }


    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun sendMessage(s: String) {
        server.sendData(Server.DataDestination.SHARE, s)

    }


}
