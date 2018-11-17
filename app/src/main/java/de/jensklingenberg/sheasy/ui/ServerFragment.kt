package de.jensklingenberg.sheasy.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.network.HTTPServerService
import kotlinx.android.synthetic.main.fragment_server.*


class ServerFragment : BaseFragment() {

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_server


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel = ViewModelProviders.of(this).get(NewCommonViewModel::class.java)

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
