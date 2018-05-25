package de.jensklingenberg.sheasy.ui.main

import android.arch.lifecycle.Observer
import android.content.ClipData
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.extension.getClipboardMangaer
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_log.*
import io.reactivex.android.schedulers.AndroidSchedulers
import de.jensklingenberg.sheasy.R.id.spinner
import com.jakewharton.rxbinding2.widget.RxAdapterView
import de.jensklingenberg.sheasy.enums.EventCategory
import de.jensklingenberg.sheasy.ui.EventLog.EventAdapter


/**
 * Created by jens on 1/4/18.
 */
class LogFragment : BaseFragment(), EventAdapter.OnTagClickListener, ITabView {
    override fun getTabName(): Int {
      return R.string.main_frag_tab_name
    }
    lateinit var profileViewModel: ProfileViewModel
    var lili= ArrayList<Event>()

    val eventAdapter=EventAdapter()





    override fun onTagClicked(tag: Event) {
      activity?.getClipboardMangaer()?.apply {
            primaryClip = ClipData.newPlainText("simple text", tag.text)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return  inflater.inflate(R.layout.fragment_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = obtainProfileViewModel()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        initObserver()

        val list = EventCategory.values().map { it.title }.filter { it.isNotEmpty() }
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(context, android.R.layout.simple_spinner_item,list)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner?.adapter = aa

        RxAdapterView.itemSelections(spinner)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe { integer -> eventAdapter.setItems(lili.filter { it.category.title.equals(list.get(integer)) }) }
        recyclerView.adapter = eventAdapter.apply {
            onDocsItemClickListener=this@LogFragment

        }


    }

    private fun initObserver() {
        profileViewModel.shareMessage.observe(this, Observer {
            lili.clear()
            lili.addAll(it?: emptyList())
            eventAdapter.apply {
                setItems(it!!)
            }
        })
    }




    companion object {
       @JvmStatic fun newInstance()= LogFragment()
        }


}