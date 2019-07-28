package de.jensklingenberg.sheasy.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.service.HTTPServerService
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), HomeContract.View {


    private val baseAdapter = BaseAdapter()
    lateinit var presenter: HomeContract.Presenter
    var toolbarMenu: Menu? = null
    var serverIcon: MenuItem? = null

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_apps

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView?.apply {
            adapter = baseAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)

            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        presenter = HomePresenter(this)
        presenter.onCreate()
    }


    override fun navigateTo(navId: Int) {
        findNavController().navigate(navId)

    }

    override fun setData(list: List<BaseDataSourceItem<*>>) {
        baseAdapter.setItems(list)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.home_options_menu, menu)
        toolbarMenu = menu
        serverIcon = menu?.findItem(R.id.menu_server)
    }

    override fun setServerState(isRunning: Boolean) {
        if (isRunning) {
            serverIcon?.setIcon(R.drawable.ic_router_green_700_24dp)

        } else {
            serverIcon?.setIcon(R.drawable.ic_router_black_24dp)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                getBaseActivity().mainActivityDrawer.toggleDrawer()
            }
            R.id.menu_server -> {
                when (item.isChecked) {
                    true -> {
                        presenter.stopService(HTTPServerService.stopIntent(requireContext()))
                        item.isChecked = false
                    }
                    false -> {
                        presenter.startService(HTTPServerService.getStartIntent(requireContext()))

                        item.isChecked = true

                    }
                }
            }
        }
        return true
    }

}