package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.apps.AppsFragment
import de.jensklingenberg.sheasy.ui.main.LogFragment
import de.jensklingenberg.sheasy.ui.main.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class RootFragment : BaseFragment() {


    lateinit var permissionOverViewFragment: PermissionOverViewFragment
    lateinit var mainFragment: MainFragment
    var fragmentPagerAdapter: FragmentPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    fun changeFragment(fragment: Fragment, keepInstance: Boolean, imageView: ImageView?) {
        val trans = childFragmentManager.beginTransaction()
        if (keepInstance) {
            trans.add(R.id.rootContainer, fragment)
        } else {
            trans.replace(R.id.rootContainer, fragment)
        }
        // trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        trans.addToBackStack(null)

        trans.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = RootFragment()
    }


}