package de.jensklingenberg.sheasy.ui.share

import android.os.Bundle
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

        recyclerView?.apply {
            recyclerView.adapter = baseAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        presenter = SharePresenter(this)
        presenter.onCreate()

        sendBtn.setOnClickListener {
            presenter.sendMessage(input.text.toString())
        }

    }

    override fun setData(items: List<BaseDataSourceItem<*>>) {
        baseAdapter.dataSource.setItems(items)
        baseAdapter.notifyDataSetChanged()

    }


}
