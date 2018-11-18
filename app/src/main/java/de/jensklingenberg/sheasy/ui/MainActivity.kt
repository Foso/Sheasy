package de.jensklingenberg.sheasy.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.network.HTTPServerService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {

    lateinit var mainActivityDrawer: MainActivityDrawer
    lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController


    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {

        when (val item = drawerItem?.tag) {

            is SideMenuEntry -> {
                navController.navigate(item.id)
                mainActivityDrawer.closeDrawer()
            }
        }

        return true
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.mainNavigationFragment).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigation()
        mainActivityDrawer = MainActivityDrawer(this)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }


    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
        setupActionBarWithNavController(navController)
        navController.addOnNavigatedListener { _, destination ->
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                mainActivityDrawer.toggleDrawer()
            }
            R.id.menu_server -> {
                when (item.isChecked) {
                    true -> {
                        mainViewModel.stopService(HTTPServerService.startIntent(this))
                        item.isChecked = false
                        item.setIcon(R.drawable.ic_storage_white_24dp)
                    }
                    false -> {
                        mainViewModel.startService(HTTPServerService.startIntent(this))

                        item.isChecked = true
                        item.setIcon(R.drawable.ic_storage_green_a700_24dp)

                    }
                }
            }
        }

        return true
    }


}