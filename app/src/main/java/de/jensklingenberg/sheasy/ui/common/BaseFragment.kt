package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ShareScreenViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.ui.RootFragment

/**
 * Created by jens on 1/4/18.
 */
open class BaseFragment : Fragment() {


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    open fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun obtainProfileViewModel(): ProfileViewModel {
        return ViewModelFactory.obtainProfileViewModel(activity)
    }

    fun obtainShareScreenViewModel(): ShareScreenViewModel {
        return ViewModelFactory.obtainShareScreenViewModel(activity)
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