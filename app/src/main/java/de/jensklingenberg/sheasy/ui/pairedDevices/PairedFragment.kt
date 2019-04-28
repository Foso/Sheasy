package de.jensklingenberg.sheasy.ui.pairedDevices

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.NoOrEmptyContentItem
import kotlinx.android.synthetic.main.fragment_apps.*


class PairedFragment : BaseFragment(), PairedContract.View {


    private val aboutAdapter = BaseAdapter()
    lateinit var pairedPresenter: PairedContract.Presenter


    /****************************************** Fragment Lifecycle methods  */

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)


    override fun getLayoutId(): Int = R.layout.fragment_paired


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setHasOptionsMenu(true)



        pairedPresenter = PairedPresenter(this)
        pairedPresenter.onCreate()
    }

    private fun setupRecycler() {
        recyclerView?.apply {
            adapter = aboutAdapter.apply {
                dataSource.emptyView =
                    NoOrEmptyContentItem("No connected Devices", R.drawable.ic_smartphone_black_24dp).toSourceItem()
            }
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                getBaseActivity().mainActivityDrawer.toggleDrawer()
            }
        }

        return true
    }

    override fun setData(list: List<BaseDataSourceItem<*>>) {
        aboutAdapter.dataSource.setItems(list)
        aboutAdapter.notifyDataSetChanged()

    }


}
