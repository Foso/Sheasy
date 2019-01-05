package de.jensklingenberg.sheasy.ui.apps

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.utils.extension.requireView
import de.jensklingenberg.sheasy.utils.extension.toSourceItem
import de.jensklingenberg.sheasy.web.model.AppInfo
import kotlinx.android.synthetic.main.fragment_apps.*
import de.jensklingenberg.sheasy.web.model.checkState
import javax.inject.Inject


class AppsFragment : BaseFragment(), OnEntryClickListener {


    private val baseAdapter = BaseAdapter()
    lateinit var appsViewModel: AppsViewModel
    @Inject
    lateinit var messageUseCase: MessageUseCase


    /****************************************** Lifecycle methods  */

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

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

    /****************************************** Class methods  */


    private fun initSearchView(menu: Menu?) {
        val search = menu?.findItem(R.id.search)?.actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

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
                it.checkState(
                    onSuccess = { appInfoList ->
                        appInfoList
                            .sortedBy { it.name }
                            .map { it.toSourceItem(this) }
                            .run {
                                baseAdapter.dataSource.setItems(this)
                                baseAdapter.notifyDataSetChanged()
                            }
                    }, onLoading = {
                        messageUseCase.show(requireView(), "Loading")
                    }
                )


            }
    }

    /****************************************** Listener methods  */


    override fun onItemClicked(payload: Any) {


    }

    override fun onMoreButtonClicked(view: View, payload: Any) {
        val item = payload as? AppInfo
        val popup = PopupMenu(requireActivity(), view)
        popup.menuInflater
            .inflate(R.menu.apps_actions, popup.menu);
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_share -> {
                    appsViewModel.shareApp(item!!)
                    //shareUseCase.share()
                }
                R.id.menu_extract->{
                    if (appsViewModel.extractApp(item!!)) {
                        messageUseCase.show(requireView(),"Succes")
                    }
                }
            }
            true
        }
        popup.show()
    }


}
