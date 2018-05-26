package de.jensklingenberg.sheasy.ui.common

import android.support.v4.app.Fragment
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.ui.RootFragment

/**
 * Created by jens on 1/4/18.
 */
open class BaseFragment : Fragment() {

    fun obtainProfileViewModel(): ProfileViewModel {
        return ViewModelFactory.obtainProfileViewModel(activity)
    }

    fun changeFragment(fragment: Fragment, keepInstance: Boolean = false) {
        if (parentFragment is RootFragment) {
            (parentFragment as RootFragment).changeFragment(
                fragment,
                keepInstance
            )
        } else throw RuntimeException(
            "there is no parentFragment that matches the type "
                    + fragment
                    + " + make sure your using types correctly"
        )


    }

}