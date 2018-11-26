package de.jensklingenberg.sheasy.ui

import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.SideMenuEntry


class MainActivityDrawer(val activity: MainActivity) {

    init {
        initDrawer()
    }


    lateinit var result: Drawer
    lateinit var headerResult: AccountHeader


    private fun initDrawer() {


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


        SideMenuEntry.values().forEachIndexed { index, sideMenuEntry ->
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


}