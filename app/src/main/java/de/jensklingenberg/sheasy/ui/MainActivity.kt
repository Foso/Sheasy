package de.jensklingenberg.sheasy.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.ui.files.FilesFragmentDirections
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {

    lateinit var mainActivityDrawer: MainActivityDrawer
    lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController

    /******************************************  Lifecycle methods  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigation()
        handleIntent(intent)
        mainActivityDrawer = MainActivityDrawer(this)
        mainViewModel = obtainViewModel(MainViewModel::class.java)
     //   requestNotificationPermission(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_options_menu, menu)
        return true
    }

    fun requestNotificationPermission(context: Context) {
        ContextCompat.startActivity(
            context,
            Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS),
            null
        )
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                mainActivityDrawer.toggleDrawer()
            }
            R.id.menu_server -> {
                when (item.isChecked) {
                    true -> {
                        mainViewModel.stopService(HTTPServerService.getIntent(this))
                        item.isChecked = false
                        item.setIcon(R.drawable.ic_router_black_24dp)
                    }
                    false -> {
                        mainViewModel.startService(HTTPServerService.getIntent(this))

                        item.isChecked = true
                        item.setIcon(R.drawable.ic_router_green_700_24dp)

                    }
                }
            }
        }

        return true
    }

    /******************************************  Listener methods  */

    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {
        when (val item = drawerItem?.tag) {

            is SideMenuEntry -> {
                mainActivityDrawer.closeDrawer()

                navController.navigate(item.navId)
            }
        }

        return true
    }

    /******************************************  Class methods  */

    override fun onSupportNavigateUp() =
        findNavController(R.id.mainNavigationFragment).navigateUp()

    fun handleIntent(intent: Intent) {
        intent.let {
            val action = it.action
            val type = it.type


            val clipData = it.clipData;
            if (clipData != null) {


                for (i in 0..clipData.itemCount) {
                    val item = clipData.getItemAt(i)
                    val uri = item.uri

                    val filePath = FilesFragmentDirections.ActionFilesFragmentSelf().setFilePath(uri.toString())

                   // var bundle = bundleOf("filePath" to uri)
                    navController.navigate(R.id.filesFragment, filePath.arguments)
                    break
                }
            }


        }
    }


    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        }
    }


}