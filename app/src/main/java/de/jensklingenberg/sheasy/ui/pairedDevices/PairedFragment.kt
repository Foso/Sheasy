package de.jensklingenberg.sheasy.ui.pairedDevices

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.GenericListItem
import de.jensklingenberg.sheasy.ui.common.GenericListItemSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import kotlinx.android.synthetic.main.fragment_apps.*


class PairedFragment : BaseFragment(), OnEntryClickListener {


    private val aboutAdapter = BaseAdapter()
    lateinit var pairedViewModel: PairedViewModel


    /****************************************** Fragment Lifecycle methods  */

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)


    override fun getLayoutId(): Int = R.layout.fragment_paired


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pairedViewModel = obtainViewModel(PairedViewModel::class.java)

        recyclerView?.apply {
            adapter = aboutAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        pairedViewModel.loadApps().map {
            GenericListItem(
                "Devicename",
                it.ip,
                R.drawable.ic_smartphone_black_24dp
            ).toSourceItem()
        }.run {
            aboutAdapter.dataSource.setItems(this)

        }


    }

    override fun onMoreButtonClicked(view: View, payload: Any) {


    }


    override fun onItemClicked(payload: Any) {
        when (val item = payload) {

            is GenericListItemSourceItem -> {
                val genericListItem = item.getPayload()
                when (genericListItem?.title) {

                }

            }
        }

    }



}
