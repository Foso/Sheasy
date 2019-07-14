package de.jensklingenberg.sheasy.ui.share

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.model.MessageEvent
import de.jensklingenberg.sheasy.model.MessageType
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
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
                        .map {
                            val message = it as MessageEvent
                            when (message.type) {
                                MessageType.INCOMING -> {
                                    IncomingMessageSourceItem(message)
                                }
                                MessageType.OUTGOING -> {
                                    OutgoingMessageSourceItem(message)
                                }
                            }

                        })
                }
            ).also { compositeDisposable.add(it) }


    }

    fun sendMessage(s: String) {
        server.sendData(Server.DataDestination.SHARE, s)

    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}
