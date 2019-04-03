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
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.extension.requireView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_apps.*
import javax.inject.Inject


class AppsFragment : BaseFragment(), OnEntryClickListener, AppsContract.View {
    override fun shareApp(appInfo: AppInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun extractApp(appInfo: AppInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(it: Throwable?) {
        messageUseCase.show(requireView(), it?.message ?: "")

    }


    private val baseAdapter = BaseAdapter()

    lateinit var presenter: AppsContract.Presenter


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

        presenter=AppsPresenter(this)
        presenter.onCreate()
        initSubscriber()


    }

    private fun initSubscriber() {





    }
    override fun setData(list: List<AppInfoSourceItem>) {
        baseAdapter.dataSource.setItems(list)
        baseAdapter.notifyDataSetChanged()

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
            .doOnNext { presenter.searchApp(it.toString()) }
            .subscribe()

    }


    /****************************************** Listener methods  */


    override fun onItemClicked(payload: Any) {}

    override fun onMoreButtonClicked(view: View, payload: Any) {
        val appInfo = payload as? AppInfo
        appInfo?.let { appInfo ->
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
                                    shareApp(appInfo)
                                }
                                R.id.menu_extract -> {
                                    if (presenter.extractApp(appInfo)) {
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