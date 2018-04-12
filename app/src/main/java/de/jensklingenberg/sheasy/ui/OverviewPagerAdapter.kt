package de.jensklingenberg.sheasy.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import de.jensklingenberg.sheasy.App

class OverviewPagerAdapter(fragmentManager: FragmentManager,
    internal var fragments: List<ITabView>) : FragmentPagerAdapter(fragmentManager) {

  // Returns total number of pages
  override fun getCount(): Int {
    return fragments.size
  }

  // Returns the fragment to display for that page
  override fun getItem(position: Int): Fragment {
    return fragments[position] as Fragment
  }


  // Returns the page title for the top indicator
  override fun getPageTitle(position: Int): CharSequence? {
    return App.instance.getString(fragments[position].getTabName())
  }
}