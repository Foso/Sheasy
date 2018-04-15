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
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.ui.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.ui.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_share_actvity.*
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by jens on 1/4/18.
 */
class ShareFragment : BaseFragment() {

    lateinit var profileViewModel: ProfileViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.activity_share_actvity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelFactory.obtainProfileViewModel(activity)



        profileViewModel.sharedFolder.observe(this, Observer {
            fileTv?.text= it

        })

    }






    companion object {
       @JvmStatic fun newInstance(): ShareFragment {
            return ShareFragment()
        }
    }

}