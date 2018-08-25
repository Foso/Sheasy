package de.jensklingenberg.sheasy.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.widget.ImageView
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment

class RootFragment : BaseFragment() {


    lateinit var permissionOverViewFragment: PermissionOverViewFragment
    lateinit var mainFragment: MainFragment
    var fragmentPagerAdapter: FragmentPagerAdapter? = null


    override fun getLayoutId() = R.layout.fragment_root


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