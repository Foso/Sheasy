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
import de.jensklingenberg.sheasy.extension.getClipboardMangaer
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.network.MyHttpServerImpl
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.network.websocket.MessageWebsocket
import de.jensklingenberg.sheasy.ui.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.ui.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by jens on 1/4/18.
 */
class MainFragment : Fragment(), EventAdapter.OnTagClickListener,ITabView{
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
        profileViewModel = ViewModelFactory.obtainProfileViewModel(activity)


        recyclerView.layoutManager = LinearLayoutManager(activity)

        initObserver()

        val intent = Intent(activity, HTTPServerService::class.java)
        profileViewModel.startService(activity as MainActivity, intent)

        btnStart.setOnClickListener {
            val pipp = Intent(MessageWebsocket.MESSAGE).apply {
                putExtra(MessageWebsocket.MESSAGE, NotificationResponse("test.package", edPath.text.toString(), "2", "3", 0L))
            }

            activity?.sendBroadcast(pipp)
        }
        btnStop?.apply {
            setOnClickListener {

            }
        }

    }

    private fun initObserver() {
        profileViewModel.shareMessage.observe(this, Observer {
            var event = EventAdapter(this, activity, it!!)
            recyclerView.adapter = event
        })
    }

    private fun initIPAddress() {
        val ip = NetworkUtils.getIP(App.instance)
        textView.text = "Meine IP Adresse: " + ip + ":" + MyHttpServerImpl.PORT
    }


    companion object {
       @JvmStatic fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}