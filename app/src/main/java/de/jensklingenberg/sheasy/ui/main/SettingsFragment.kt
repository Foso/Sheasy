package de.jensklingenberg.sheasy.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel

/**
 * Created by jens on 1/4/18.
 */
class SettingsFragment : BaseFragment(), ITabView {
    override fun getTabName(): Int {
      return R.string.main_frag_tab_name
    }
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = obtainProfileViewModel()

    }

    companion object {
       @JvmStatic fun newInstance()= SettingsFragment()
        }


}