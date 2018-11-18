package de.jensklingenberg.sheasy.ui

import android.os.Bundle
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.fragment_server.*


class HomeFragment : BaseFragment() {

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_server


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel: MainViewModel = obtainViewModel(MainViewModel::class.java)

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

