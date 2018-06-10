package de.jensklingenberg.sheasy.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.data.viewmodel.PermissionViewModel
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.model.Status
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.utils.NetworkUtils
import de.jensklingenberg.sheasy.utils.extension.nonNull
import kotlinx.android.synthetic.main.fragment_permission_overview.*
import org.threeten.bp.LocalDateTime


/**
 * Created by jens on 1/4/18.
 */
class PermissionOverViewFragment : BaseFragment(), ITabView {
    lateinit var permissionViewModel: PermissionViewModel
    lateinit var profileViewModel: CommonViewModel

    override fun getTabNameResId(): Int {
        return R.string.main_frag_tab_name
    }

    override fun getLayoutId() = R.layout.fragment_permission_overview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionViewModel = ViewModelFactory.obtainPermissionViewModel(activity)
        initObserver()
        permissionViewModel.checkStoragePermission()
        permissionViewModel.checkNotifcationPermission(context!!)
        permissionViewModel.checkContactsPermission()
        profileViewModel = obtainProfileViewModel()
        profileViewModel.startService(Intent(activity, HTTPServerService::class.java))
        initIPAddress()


    }

    private fun initIPAddress() {
        serverEd.text = "Meine IP Adresse: " + NetworkUtils.getIP(App.instance) + ":" + App.port
    }

    private fun initObserver() {

        serverBtn?.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    profileViewModel.startService(Intent(activity, HTTPServerService::class.java))
                    App.instance.sendBroadcast(
                        Event(
                            EventCategory.SERVER,
                            "started",
                            LocalDateTime.now().toString()
                        )
                    )

                    Intent(MySharedMessageBroadcastReceiver.MESSAGE).apply {
                        putExtra(
                            MySharedMessageBroadcastReceiver.MESSAGE,
                            NotificationResponse(
                                "test.package",
                                serverEd.text.toString(),
                                "2",
                                "3",
                                0L
                            )
                        )
                        activity?.sendBroadcast(this)
                    }

                }

                false -> {
                    App.instance.sendBroadcast(EventCategory.SERVER, "stopped")

                    profileViewModel.stopService(Intent(activity, HTTPServerService::class.java))

                }


            }
        }





        permissionViewModel.storagePermission.nonNull().observe(this, Observer {

            when (it?.status) {
                Status.SUCCESS -> {
                    when (it.data) {
                        true -> {
                            toggleButton.isActivated = true
                        }
                        false -> {
                            toggleButton.apply {
                                isActivated = false
                                setOnClickListener {
                                    permissionViewModel.requestStorage(activity as AppCompatActivity)
                                }
                            }
                        }
                    }

                }
            }
        })

        permissionViewModel.notificationPermissionStatus.observe(this, Observer { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    when (it.data) {
                        true -> {
                            notificationBtn?.apply {
                                isActivated = true
                                setOnClickListener {
                                    permissionViewModel.disableNotificationPermission()
                                }
                            }
                        }
                        false -> {
                            notificationBtn?.apply {
                                isActivated = false
                                setOnClickListener {
                                    permissionViewModel.requestNotificationPermission(context)
                                }
                            }
                        }
                        null -> ""
                    }
                }
            }
        })


    }


    companion object {
        @JvmStatic
        fun newInstance() = PermissionOverViewFragment()
    }

}