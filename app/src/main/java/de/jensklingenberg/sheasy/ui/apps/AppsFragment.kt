package de.jensklingenberg.sheasy.ui.apps

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.utils.extension.toSourceItem
import kotlinx.android.synthetic.main.fragment_apps.*


class AppsFragment : BaseFragment() {

    private val baseAdapter = BaseAdapter()
    lateinit var appsViewModel: AppsViewModel

    override fun getLayoutId(): Int = R.layout.fragment_apps


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
        appsViewModel = obtainViewModel(AppsViewModel::class.java)

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

        observeAppsViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.fragment_apps_options_menu, menu)
        initSearchView(menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initSearchView(menu: Menu?) {
        val search = menu?.findItem(R.id.search)?.actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    appsViewModel.searchApp(newText)
                }
                return true
            }

        })
    }


    private fun observeAppsViewModel() {
        appsViewModel
            .getApps()
            .nonNull()
            .observe {
                it
                    .sortedBy { it.name }
                    .map { it.toSourceItem() }
                    .run {
                        baseAdapter.dataSource.setItems(this)
                        baseAdapter.notifyDataSetChanged()
                    }

            }
    }
}