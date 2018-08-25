package de.jensklingenberg.sheasy.ui.common

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.viewmodel.*
import de.jensklingenberg.sheasy.ui.RootFragment
import javax.inject.Inject

/**
 * Created by jens on 1/4/18.
 */
open class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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

    fun obtainPermissionViewModel(): PermissionViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[PermissionViewModel::class.java]
    }


    fun obtainProfileViewModel(): CommonViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[CommonViewModel::class.java]
    }

    fun obtainAppsViewModel(): AppsViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[AppsViewModel::class.java]
    }

    fun obtainNetworkViewModel(): NetworkViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[NetworkViewModel::class.java]
    }

    fun obtainShareScreenViewModel(): ShareScreenViewModel {
        return ViewModelProviders.of(this, viewModelFactory)[ShareScreenViewModel::class.java]
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

