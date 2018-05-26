package de.jensklingenberg.sheasy.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.PermissionViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.model.Status
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.fragment_permission_overview.*


/**
 * Created by jens on 1/4/18.
 */
class PermissionOverViewFragment : Fragment(), ITabView {
    lateinit var permissiViewModel: PermissionViewModel
    override fun getTabName(): Int {
        return R.string.main_frag_tab_name
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_permission_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissiViewModel = ViewModelFactory.obtainPermissionViewModel(activity)
        initObserver()
        permissiViewModel.checkStoragePermission()
        permissiViewModel.checkNotifcationPermission(context!!)
        permissiViewModel.checkContactsPermission()


    }

    private fun initObserver() {
        permissiViewModel.storagePermission.observe(this, Observer {

            when (it?.status) {
                Status.SUCCESS -> {
                    when (it.data) {
                        true -> {
                            toggleButton.isActivated = true
                        }
                        false -> {
                            toggleButton.isActivated = false
                        }
                    }

                }
            }
        })

        permissiViewModel.notificationPermissionStatus.observe(this, Observer { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    when (it.data) {
                        true -> {
                            notificationBtn?.apply {
                                isActivated = true
                                setOnClickListener {
                                    permissiViewModel.disableNotificationPermission()
                                }
                            }
                        }
                        false -> {
                            notificationBtn?.apply {
                                isActivated = false
                                setOnClickListener {
                                    permissiViewModel.requestNotificationPermission(context)
                                }
                            }
                        }
                        null -> ""
                    }
                }
            }
        })


        permissiViewModel.contactsPermissionStatus.observe(this, Observer { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    when (it.data) {
                        true -> {
                            contactsBtn?.apply {
                                isActivated = true
                                setOnClickListener {
                                    // profileViewModel.disableNotificationPermission()
                                }
                            }
                        }
                        false -> {
                            contactsBtn?.apply {
                                isActivated = false
                                setOnClickListener {
                                    permissiViewModel.requestContactsPermission(activity as AppCompatActivity)
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