package de.jensklingenberg.sheasy.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.jensklingenberg.sheasy.R

class NewMainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {

    enum class SideMenuEntry(val title: String) {
        SETTINGS("Settings"),
        SHARE("Shared"),
        HELP("HELP")
    }

    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {
        when (drawerItem?.tag) {
            SideMenuEntry.SETTINGS -> {
                navigation.toSettings()
                closeDrawer()

            }
            SideMenuEntry.SHARE -> {

                closeDrawer()

            }

            SideMenuEntry.HELP -> {

                closeDrawer()

            }
        }
        return true
    }


    lateinit var result: Drawer
    lateinit var headerResult: AccountHeader

    val navigation: Navigation  by lazy {
        Navigation(
            supportFragmentManager,
            R.id.container
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.toServer()
        initDrawer()
    }

    private fun initDrawer() {


        // Create the AccountHeader
        headerResult = AccountHeaderBuilder()
            .withActivity(this)
            // .withHeaderBackground(R.drawable.blue_button)
            .withSelectionListEnabledForSingleProfile(false)
            .withOnAccountHeaderListener(
                { _, _, _ -> false })
            .build()


        result = DrawerBuilder()
            .withAccountHeader(headerResult)
            .withActivity(this)
            .withOnDrawerItemClickListener(this)
            .build()


        SideMenuEntry.values().forEachIndexed { index, sideMenuEntry ->
            result.addItem(
                SecondaryDrawerItem().withIdentifier(index.toLong()).withName(
                    sideMenuEntry.title
                ).withTag(sideMenuEntry)
            )
        }


    }

    fun showMenu() {
        result.openDrawer()

    }

    fun closeDrawer() {
        result.closeDrawer()
    }

}