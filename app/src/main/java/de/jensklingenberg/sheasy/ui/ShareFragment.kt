package de.jensklingenberg.sheasy.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.NetworkViewModel
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.ui.devices.DeviceAdapter
import kotlinx.android.synthetic.main.activity_share_actvity.*

/**
 * Created by jens on 1/4/18.
 */
class ShareFragment : BaseFragment(), ITabView {
    override fun getTabNameResId() = 0


    val deviceAdapter = DeviceAdapter()

    lateinit var profileViewModel: CommonViewModel
    lateinit var networkViewModel: NetworkViewModel


    override fun getLayoutId() = R.layout.activity_share_actvity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = obtainProfileViewModel()

        networkViewModel = obtainNetworkViewModel()

        recyclerView.adapter = deviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)


        markdownView.setOnClickListener {
            networkViewModel.findDevices(App.instance)
        }

        networkViewModel.devices.observe(this, Observer {
            deviceAdapter.setItems(it!!.data!!)

        })

    }


    companion object {
        @JvmStatic
        fun newInstance() = ShareFragment()

    }

}