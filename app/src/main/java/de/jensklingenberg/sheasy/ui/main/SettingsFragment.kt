package de.jensklingenberg.sheasy.ui.main

import android.os.Bundle
import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView

/**
 * Created by jens on 1/4/18.
 */
class SettingsFragment : BaseFragment(), ITabView {
    override fun getTabNameResId() = R.string.settings

    lateinit var profileViewModel: CommonViewModel

    override fun getLayoutId() = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = obtainProfileViewModel()

    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }


}