package de.jensklingenberg.sheasy.ui

import android.arch.lifecycle.Observer
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.extension.getClipboardMangaer
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.network.MyHttpServerImpl
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.network.websocket.MessageWebsocket
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.ui.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.ui.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.utils.NetworkUtils
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by jens on 1/4/18.
 */
class MainFragment : BaseFragment(), EventAdapter.OnTagClickListener, ITabView {
    override fun getTabName(): Int {
      return R.string.main_frag_tab_name
    }
    lateinit var profileViewModel: ProfileViewModel

    override fun onTagClicked(tag: Event) {
      activity?.getClipboardMangaer()?.apply {
            primaryClip = ClipData.newPlainText("simple text", tag.text)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edPath.setText(Environment.getExternalStorageDirectory().toString())
        initIPAddress()
        profileViewModel = obtainProfileViewModel()
        profileViewModel.startService( Intent(activity, HTTPServerService::class.java))




        btnStart.setOnClickListener {
          Intent(MySharedMessageBroadcastReceiver.MESSAGE).apply {
                putExtra(MySharedMessageBroadcastReceiver.MESSAGE, NotificationResponse("test.package", edPath.text.toString(), "2", "3", 0L))
                activity?.sendBroadcast(this)
            }

        }
        btnStop?.apply {
            setOnClickListener {

            }
        }

    }



    private fun initIPAddress() {
        textView.text = "Meine IP Adresse: " + NetworkUtils.getIP(App.instance) + ":" + MyHttpServerImpl.PORT
    }


    companion object {
       @JvmStatic fun newInstance()=MainFragment()

    }

}