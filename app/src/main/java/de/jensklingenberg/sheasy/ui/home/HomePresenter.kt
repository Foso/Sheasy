package de.jensklingenberg.sheasy.ui.home

import android.app.Application
import android.content.Intent
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.sideMenuEntries
import de.jensklingenberg.sheasy.ui.common.GenericListItem
import de.jensklingenberg.sheasy.ui.common.GenericListItemSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter, OnEntryClickListener {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun startService(intent: Intent) {
        application.startService(intent)


    }

    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun stopService(intent: Intent) {
        application.stopService(intent)

    }

    override fun onMoreButtonClicked(view: View, payload: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClicked(payload: Any) {
        when (val item = payload) {

            is GenericListItemSourceItem -> {
                val genericListItem = item.getPayload()
                sideMenuEntries
                    .first { it.title == genericListItem?.title }
                    .run {
                        view.navigateTo(this.navId)

                    }
            }
        }

    }

    override fun onCreate() {

        sideMenuEntries
            .filter { it.title != "Home" }.toList()
            .map {
                GenericListItem(it.title, "", it.iconRes).toSourceItem(this)
            }.run {
                view.setData(this)
            }

    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}