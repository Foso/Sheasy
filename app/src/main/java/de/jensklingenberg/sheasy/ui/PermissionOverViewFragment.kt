package de.jensklingenberg.sheasy.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.model.Status
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.fragment_permission_overview.*


/**
 * Created by jens on 1/4/18.
 */
class PermissionOverViewFragment : Fragment(), ITabView {
    override fun getTabName(): Int {
        return R.string.main_frag_tab_name
    }

    lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_permission_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelFactory.obtainProfileViewModel(activity)

        initObserver()
        profileViewModel.checkStoragePermission()
        profileViewModel.checkNotifcationPermission(context!!)
        profileViewModel.checkContactsPermission()


    }

    private fun initObserver() {
        profileViewModel.storagePermission.observe(this, Observer {

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

        profileViewModel.notificationPermissionStatus.observe(this, Observer { it ->
            when (it?.status) {
                Status.SUCCESS -> {
                    when (it.data) {
                        true -> {
                            notificationBtn?.apply {
                                isActivated = true
                                setOnClickListener {
                                    profileViewModel.disableNotificationPermission()
                                }
                            }
                        }
                        false -> {
                            notificationBtn?.apply {
                                isActivated = false
                                setOnClickListener {
                                    profileViewModel.requestNotificationPermission(context)
                                }
                            }
                        }
                        null -> ""
                    }
                }
            }
        })


        profileViewModel.contactsPermissionStatus.observe(this, Observer { it ->
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
                                    profileViewModel.requestContactsPermission(activity as AppCompatActivity)
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
        fun newInstance(): PermissionOverViewFragment {
            val args = Bundle()
            val fragment = PermissionOverViewFragment()
            fragment.arguments = args
            return fragment
        }
    }

}