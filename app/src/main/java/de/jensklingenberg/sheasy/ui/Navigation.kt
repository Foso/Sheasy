package de.jensklingenberg.sheasy.ui


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mikepenz.aboutlibraries.LibsBuilder
import de.jensklingenberg.sheasy.R


interface INavigation {
    fun toApps()
    fun toSettings()
    fun toOpenSource()
    fun toLogFragment()
    fun toFiles()
    fun toHelp()
    fun toShare()
    fun toRoot()
    fun toServer()
}

class Navigation(val fragmentManager: FragmentManager, id: Int) :
    INavigation {
    override fun toSettings() {


    }

    private fun changeFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.rootContainer, fragment)
            addToBackStack(null)
            commit()
        }

    }

    override fun toRoot() {
        changeFragment(RootFragment.newInstance())
    }

    override fun toApps() {
    }


    override fun toServer() {
        changeFragment(newFragmentInstance<ServerFragment>())
    }

    override fun toOpenSource() {
        changeFragment(LibsBuilder().supportFragment())
    }

    override fun toLogFragment() {
    }

    override fun toFiles() {
    }

    override fun toHelp() {

    }

    override fun toShare() {
    }


}