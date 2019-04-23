package de.jensklingenberg.sheasy.ui.home

import android.app.Application
import android.content.Intent
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.sideMenuEntries
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.ui.common.GenericListItem
import de.jensklingenberg.sheasy.ui.common.GenericListItemSourceItem
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter(val view: HomeContract.View) : HomeContract.Presenter {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }


    val homeEntries = listOf(
        SideMenuEntry("Apps", R.id.appsFragment, R.drawable.ic_android_black_24dp),
        SideMenuEntry("Files", R.id.filesFragment, R.drawable.ic_smartphone_black_24dp),
        SideMenuEntry("Paired", R.id.pairedFragment, R.drawable.ic_folder_grey_700_24dp),
        SideMenuEntry("Settings", R.id.settingsFragment, R.drawable.ic_settings_black_24dp),
        SideMenuEntry("Share", R.id.shareFragment, R.drawable.ic_settings_black_24dp),
        SideMenuEntry("About", R.id.aboutFragment, R.drawable.ic_info_outline_black_24dp)
    )

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

        homeEntries
            .filter { it.title != "Home" }
            .toList()
            .map {
                GenericListItem(it.title, "", it.iconRes).toSourceItem(this)
            }.run {
                view.setData(this)
            }

    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun startService(intent: Intent) {
        application.startService(intent)


    }
}