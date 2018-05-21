package de.jensklingenberg.sheasy.ui

import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.main.LogFragment
import de.jensklingenberg.sheasy.ui.main.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class RootFragment : BaseFragment(){


    lateinit var permissionOverViewFragment: PermissionOverViewFragment
    lateinit var mainFragment: MainFragment
    var fragmentPagerAdapter: FragmentPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.fragment_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()

    }


    fun initViewPager() {
        mainFragment = MainFragment.newInstance()
        val logFragment = LogFragment.newInstance()
        val settingsFragment = SettingsFragment.newInstance()

        permissionOverViewFragment=PermissionOverViewFragment.newInstance()
        fragmentPagerAdapter =  OverviewPagerAdapter(childFragmentManager, listOf(mainFragment,logFragment,permissionOverViewFragment))
        viewpager.adapter = fragmentPagerAdapter
    }

    companion object {
        @JvmStatic fun newInstance()=RootFragment()
        }

}