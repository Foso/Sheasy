package de.jensklingenberg.sheasy.ui.eventlog

import android.content.Context
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class EventLogPresenter(val view: EventLogContract.View) : EventLogContract.Presenter, OnEntryClickListener {


    @Inject
    lateinit var context: Context

    @Inject
    lateinit var eventDataSource: EventDataSource

    val compositeDisposable = CompositeDisposable()

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCreate() {

        compositeDisposable.add(
            eventDataSource.getEvents().subscribeBy(
                onNext = {
                    view.setData(it.map { it.toSourceItem() })
                }
            )
        )


    }

    override fun onMoreButtonClicked(view: View, payload: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClicked(payload: Any) {
        view.onItemClicked(payload)
    }

}