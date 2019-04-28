package de.jensklingenberg.sheasy.ui.share

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_share.*


class ShareFragment : BaseFragment(), ShareContract.View {


    private val baseAdapter = BaseAdapter()
    lateinit var presenter: SharePresenter


    /****************************************** Fragment Lifecycle methods  */

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)


    override fun getLayoutId(): Int = R.layout.fragment_share


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()

        presenter = SharePresenter(this)
        presenter.onCreate()

        sendBtn.setOnClickListener {
            presenter.sendMessage(input.text.toString())
        }

    }

    private fun setupRecyclerView() {
        recyclerView?.apply {
            adapter = baseAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
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

    override fun setData(items: List<BaseDataSourceItem<*>>) {
        baseAdapter.dataSource.setItems(items)
        baseAdapter.notifyDataSetChanged()

    }


}
