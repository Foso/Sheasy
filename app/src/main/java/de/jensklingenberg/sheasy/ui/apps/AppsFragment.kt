package de.jensklingenberg.sheasy.ui.apps

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.appcompat.itemClicks
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.utils.extension.requireView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_apps.*
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
        setHasOptionsMenu(true)
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

        initSubscriber()


    }

    private fun initSubscriber() {
        subscribe(
            appsViewModel.getApps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = { appList ->
                    appList.data!!
                        .sortedBy { it.name }
                        .map { it.toSourceItem(this) }
                        .run {
                            baseAdapter.dataSource.setItems(this)
                            baseAdapter.notifyDataSetChanged()
                        }
                })
        )

        subscribe(
            appsViewModel.getSnackbar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    messageUseCase.show(requireView(), it.message ?: "")
                }
                .subscribe()
        )


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
        search.queryTextChanges()
            .rxBindingSubscriber()
            .doOnNext { appsViewModel.searchApp(it.toString()) }
            .subscribe()

    }


    /****************************************** Listener methods  */


    override fun onItemClicked(payload: Any) {}

    override fun onMoreButtonClicked(view: View, payload: Any) {
        val appInfo = payload as? AppInfo
        appInfo?.let {appInfo->
            val popup = PopupMenu(requireActivity(), view)
                .apply {
                    menuInflater
                        .inflate(R.menu.apps_actions, menu)
                }
                .also {
                    it.itemClicks()
                        .rxBindingSubscriber()
                        .doOnNext { menuItem ->
                            when (menuItem.itemId) {
                                R.id.menu_share -> {
                                    appsViewModel.shareApp(appInfo)
                                }
                                R.id.menu_extract -> {
                                    if (appsViewModel.extractApp(appInfo)) {
                                        messageUseCase.show(requireView(), "Succes")
                                    }
                                }
                            }
                        }.subscribe()
                }
            popup.show()
        }

    }


}


fun <T> Observable<T>.rxBindingSubscriber(): Observable<T> {
    return this.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
}