package de.jensklingenberg.sheasy.ui.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.ui.MainViewModel
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_server.*


class SettingsFragment : BaseFragment() {

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_server


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        serverSw.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    mViewModel.startService(HTTPServerService.startIntent(activity))

                }
                false -> {
                    mViewModel.stopService(HTTPServerService.startIntent(activity))

                }
            }
        }


    }


}
