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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {

    lateinit var mainActivityDrawer: MainActivityDrawer
    lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController

    val compositeDisposable = CompositeDisposable()
    var toolbarMenu : Menu?=null


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

    fun requestNotificationPermission(context: Context) {
        ContextCompat.startActivity(
            context,
            Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS),
            null
        )
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