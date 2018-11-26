package de.jensklingenberg.sheasy.ui.settings

import android.os.Bundle
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment


class SettingsFragment : BaseFragment() {

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_settings


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



    }


}
