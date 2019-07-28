package de.jensklingenberg.sheasy.ui

import android.widget.CompoundButton
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SwitchDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.sideMenuEntries
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.service.HTTPServerService
import de.jensklingenberg.sheasy.ui.common.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class MainActivityDrawer(private val activity: MainActivity) : OnCheckedChangeListener {


    lateinit var result: Drawer
    lateinit var headerResult: AccountHeader
    val compositeDisposable = CompositeDisposable()
    lateinit var serverSwitch: SwitchDrawerItem

    init {
        initializeDagger()

        initDrawer()

        Server.serverRunning.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribeBy(onNext = { running ->
                if (running) {
                    if (!serverSwitch.isChecked) {
                        serverSwitch.withChecked(true)
                        result.updateItem(serverSwitch)
                    }

                } else {
                    if (serverSwitch.isChecked) {
                        serverSwitch.withChecked(false)
                        result.updateItem(serverSwitch)
                    }
                }

            }).addTo(compositeDisposable)


    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCheckedChanged(drawerItem: IDrawerItem<*, *>?, buttonView: CompoundButton?, isChecked: Boolean) {


        when (drawerItem?.tag) {
            R.string.server -> {
                when (isChecked) {
                    true -> {
                        activity.startService(HTTPServerService.getStartIntent(activity))
                    }
                    false -> {

                        activity.stopService(HTTPServerService.stopIntent(activity))

                    }
                }
            }
        }


    }


    private fun initDrawer() {
        serverSwitch = SwitchDrawerItem()
            .withName(R.string.server)
            .withOnCheckedChangeListener(this)
            .withTag(R.string.server)

        // Create the AccountHeader
        headerResult = AccountHeaderBuilder()
            .withActivity(activity)
            .withHeaderBackground(R.color.primary)
            .withSelectionListEnabledForSingleProfile(false)
            .build()


        result = DrawerBuilder()
            .withAccountHeader(headerResult)
            .withActivity(activity)
            .withSliderBackgroundColorRes(R.color.md_white_1000)
            .withOnDrawerItemClickListener(activity)
            .build()



        result.addItem(serverSwitch)

        sideMenuEntries.forEachIndexed { index, sideMenuEntry ->
            result.addItem(
                SecondaryDrawerItem()
                    .withIdentifier(index.toLong())
                    .withName(sideMenuEntry.title)
                    .withTextColorRes(R.color.md_dark_background)
                    .withTag(sideMenuEntry)
                    .withIcon(sideMenuEntry.iconRes)

            )
        }


    }


    fun showMenu() {
        result.openDrawer()
    }

    fun closeDrawer() {
        result.closeDrawer()
    }

    fun toggleDrawer() {
        when (result.isDrawerOpen) {
            true -> closeDrawer()
            false -> showMenu()
        }

    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }


}