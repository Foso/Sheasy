package de.jensklingenberg.sheasy.ui.main

import android.arch.lifecycle.Observer
import android.content.ClipData
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.utils.extension.getClipboardMangaer
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.ui.EventLog.EventAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.fragment_share_with.*

/**
 * Created by jens on 1/4/18.
 */
class ShareWithFragment : BaseFragment(), EventAdapter.OnTagClickListener, ITabView {
    override fun getTabNameResId(): Int {
        return R.string.main_frag_tab_name
    }

    lateinit var profileViewModel: ProfileViewModel

    override fun onTagClicked(tag: Event) {
        activity?.getClipboardMangaer()?.apply {
            primaryClip = ClipData.newPlainText("simple text", tag.text)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_share_with, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = obtainProfileViewModel()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        initObserver()


    }

    private fun initObserver() {
        profileViewModel.shareMessage.observe(this, Observer {
            recyclerView.adapter = EventAdapter()
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = ShareWithFragment()
    }


}