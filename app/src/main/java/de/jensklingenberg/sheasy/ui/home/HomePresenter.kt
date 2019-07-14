package de.jensklingenberg.sheasy.ui.home

import android.app.Application
import android.content.Intent
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.sideMenuEntries
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.ui.common.GenericListItem
import de.jensklingenberg.sheasy.ui.common.GenericListItemSourceItem
import de.jensklingenberg.sheasy.ui.common.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }


    val homeEntries = listOf(
        SideMenuEntry(R.string.Apps_Title, R.id.appsFragment, R.drawable.ic_android_black_24dp),
        SideMenuEntry(R.string.Files_Title, R.id.filesFragment, R.drawable.ic_smartphone_black_24dp),
        SideMenuEntry(R.string.Paired_Title, R.id.pairedFragment, R.drawable.ic_folder_grey_700_24dp),
        SideMenuEntry(R.string.Settings_Title, R.id.settingsFragment, R.drawable.ic_settings_black_24dp),
        //  SideMenuEntry(R.string.Share_Title, R.id.shareFragment, R.drawable.ic_settings_black_24dp),
        SideMenuEntry(R.string.About_Title, R.id.aboutFragment, R.drawable.ic_info_outline_black_24dp)
    )

    override fun onCreate() {
        homeEntries
            .toList()
            .map {
                GenericListItemSourceItem(GenericListItem(application.getString(it.title), "", it.iconRes), this)
            }.run {
                view.setData(this)
            }

        Server.serverRunning.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(onNext = { running ->
                view.setServerState(running)

            }).addTo(compositeDisposable)

    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onMoreButtonClicked(view: View, payload: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClicked(payload: Any) {
        when (val item = payload) {

            is GenericListItemSourceItem -> {
                val genericListItem = item.getPayload()
                sideMenuEntries
                    .first { application.getString(it.title) == genericListItem?.title }
                    .run {
                        view.navigateTo(this.navId)

                    }
            }
        }

    }


    override fun startService(intent: Intent) {
        application.startService(intent)
    }

    override fun stopService(intent: Intent) {
        application.stopService(intent)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}