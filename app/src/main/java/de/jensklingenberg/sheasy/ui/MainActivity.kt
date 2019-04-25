package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.ui.files.FilesFragmentDirections
import de.jensklingenberg.sheasy.utils.PermissionUtils
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {

    lateinit var mainActivityDrawer: MainActivityDrawer
    lateinit var navController: NavController

    @Inject
    lateinit var permissionUtils: PermissionUtils

    private val REQUEST_CAMERA_PERMISSION = 1


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    @Inject
    lateinit var shareUseCase: ShareUseCase


    val compositeDisposable = CompositeDisposable()
    var toolbarMenu: Menu? = null


    /******************************************  Lifecycle methods  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigation()
        handleIntent(intent)
        mainActivityDrawer = MainActivityDrawer(this)
        //   requestNotificationPermission(this)


        permissionUtils.requestPermission(this, REQUEST_CAMERA_PERMISSION)

    }


    /******************************************  Listener methods  */

    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {
        when (val item = drawerItem?.tag) {

            is SideMenuEntry -> {

                if (item.navId == -1) {
                    when (item.title) {
                        getString(R.string.side_menu_share_app) -> {

                            return true
                        }

                    }
                } else {
                    mainActivityDrawer.closeDrawer()

                    navController.navigate(item.navId)
                }

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

                    // var bundle = bundleOf("fileResponse1" to uri)
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