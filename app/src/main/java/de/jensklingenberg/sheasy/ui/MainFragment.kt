package de.jensklingenberg.sheasy.ui

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.utils.extension.getClipboardMangaer
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.ui.EventLog.EventAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_main.*
import org.threeten.bp.LocalDateTime

/**
 * Created by jens on 1/4/18.
 */
class MainFragment : BaseFragment(), EventAdapter.OnTagClickListener, ITabView {
    override fun getTabNameResId(): Int {
        return R.string.main_frag_tab_name
    }

    lateinit var profileViewModel: ProfileViewModel

    override fun onTagClicked(tag: Event) {
        activity?.getClipboardMangaer()?.apply {
            primaryClip = ClipData.newPlainText("simple text", tag.text)
        }
    }

    override fun getLayoutId() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edPath.setText(Environment.getExternalStorageDirectory().toString())
        initIPAddress()
        profileViewModel = obtainProfileViewModel()
        // profileViewModel.startService(Intent(activity, HTTPServerService::class.java))

        // markdownView.loadMarkdownFile("https://raw.githubusercontent.com/wiki/Foso/Notes/Android-Studio.md")
        serverSwitch?.setOnCheckedChangeListener { buttonView, isChecked ->
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
                                edPath.text.toString(),
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


    }


    private fun initIPAddress() {
        textView.text = "Meine IP Adresse: " + NetworkUtils.getIP(App.instance) + ":" + App.port
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }

    fun handleSendImage(intent: Intent) {
        val imageUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }


    fun handleSendMultipleImages(intent: Intent) {
        val imageUris = intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
            val path = imageUris.first().path
            profileViewModel.setSharedFolder(path)
            changeFragment(ShareFragment.newInstance(), false)
        }
    }

}