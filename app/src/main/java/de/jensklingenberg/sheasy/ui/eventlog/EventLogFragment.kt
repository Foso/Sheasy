package de.jensklingenberg.sheasy.ui.eventlog

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_apps.*


class EventLogFragment : BaseFragment(), EventLogContract.View {


    private val aboutAdapter = BaseAdapter()

    lateinit var presenter: EventLogPresenter

    /****************************************** Fragment Lifecycle methods  */


    override fun getLayoutId(): Int = R.layout.fragment_about


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView?.apply {
            adapter = aboutAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        presenter = EventLogPresenter(this)
        presenter.onCreate()

    }

    /****************************************** Listener methods  */


    override fun onItemClicked(payload: Any) {
        when (val item = payload) {


        }

    }


    override fun setData(items: List<BaseDataSourceItem<*>>) {
        aboutAdapter.setItems(items)


    }


}
