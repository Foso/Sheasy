package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.ui.apps.AppsFragment
import de.jensklingenberg.sheasy.ui.filemanager.FilesFragment
import de.jensklingenberg.sheasy.ui.main.LogFragment
import de.jensklingenberg.sheasy.ui.main.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), Drawer.OnDrawerItemClickListener {

    val PAGE_ID_0 = 0
    val PAGE_ID_1 = 1
    val PAGE_ID_2 = 2

    override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*, *>?): Boolean {

        return true
    }

    lateinit var permissionOverViewFragment: PermissionOverViewFragment
    lateinit var mainFragment: MainFragment
    var fragmentPagerAdapter: FragmentPagerAdapter? = null
    lateinit var profileViewModel: ProfileViewModel

    companion object {
        val REQUEST_PERMISSIONS = 100

    }

    lateinit var result: Drawer
    lateinit var headerResult: AccountHeader


    fun initViewPager() {
        mainFragment = MainFragment.newInstance()
        val logFragment = LogFragment.newInstance()
        val settingsFragment = SettingsFragment.newInstance()
        val appsFragment = AppsFragment.newInstance()
        val filesFragment = FilesFragment.newInstance()

        permissionOverViewFragment = PermissionOverViewFragment.newInstance()
        fragmentPagerAdapter = OverviewPagerAdapter(
            supportFragmentManager,
            listOf(mainFragment, filesFragment, appsFragment, permissionOverViewFragment)
        )
        viewpager.adapter = fragmentPagerAdapter
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

        result.addItem(
            SecondaryDrawerItem().withIdentifier(1).withName(
                "Hallo"
            )
        )

        result.openDrawer()
    }

    private fun initBottomBar() {
        navigation?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_location -> {


                    viewpager.setCurrentItem(PAGE_ID_0, false)


                }
                R.id.menu_profile -> {

                    viewpager.setCurrentItem(PAGE_ID_1, false)


                }
                R.id.menu_catalog -> {

                    viewpager.setCurrentItem(PAGE_ID_2, false)


                }

            }

            true
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        profileViewModel = ViewModelFactory.obtainProfileViewModel(this)
        initViewPager()
        initDrawer()
        initBottomBar()
        when (intent.extras) {
            null -> {
                // changeFragment(RootFragment.newInstance(), false)

            }
            else -> {
                intent?.let {
                    val action = it.action
                    val type = it.type

                    when (action) {
                        Intent.ACTION_SEND -> {
                            //handleSendImage(it)

                            Toast.makeText(this, "Hallo", Toast.LENGTH_LONG).show()
                        }

                        Intent.ACTION_SEND_MULTIPLE -> {
                            //handleSendMultipleImages(it)
                        }
                        else -> {
                        }
                    }
                }
            }
        }


        //initViewPager()
    }

    fun showMenu() {
        result.openDrawer()

    }


}

