package de.jensklingenberg.sheasy.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_apps.*


class SettingsFragment : BaseFragment(), SettingsContract.View {

    private val settingsAdapter = BaseAdapter()

    private lateinit var presenter: SettingsContract.Presenter


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView?.apply {
            adapter = settingsAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        presenter = SettingsPresenter(this)
        presenter.onCreate()
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
        settingsAdapter.setItems(items)

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}
