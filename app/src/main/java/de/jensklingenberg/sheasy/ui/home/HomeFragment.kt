package de.jensklingenberg.sheasy.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.GenericListItem
import de.jensklingenberg.sheasy.model.GenericListItemSourceItem
import de.jensklingenberg.sheasy.model.SideMenuEntry
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.utils.extension.toSourceItem
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), OnEntryClickListener {

    private val baseAdapter = BaseAdapter()
    lateinit var homeViewModel: HomeViewModel

    override fun getLayoutId(): Int = R.layout.fragment_apps

    override fun onItemClicked(payload: Any) {
        when (val item = payload) {

            is GenericListItemSourceItem -> {
                val genericListItem = item.getPayload()
                SideMenuEntry
                    .values()
                    .first { it.title == genericListItem?.title }
                    .run {
                        findNavController().navigate(this.navId)

                    }
            }
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
        homeViewModel = obtainViewModel(HomeViewModel::class.java)

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

        observeHomeViewModel()
    }


    private fun observeHomeViewModel() {
        homeViewModel
            .getEntries()
            .map {
                GenericListItem(it.title, "", it.iconRes).toSourceItem(this)
            }
            .run {
                baseAdapter.dataSource.setItems(this)
                baseAdapter.notifyDataSetChanged()
            }


    }
}