package de.jensklingenberg.sheasy.ui.about

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.model.GenericListItem
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.utils.extension.toSourceItem
import kotlinx.android.synthetic.main.fragment_apps.*


class AboutFragment : BaseFragment(), OnEntryClickListener {


    override fun onItemClicked(payload: Any) {
        val i = payload
        1 == 1

    }

    private val aboutAdapter = BaseAdapter()
    lateinit var aboutViewModel: AboutViewModel

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_about


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutViewModel = obtainViewModel(AboutViewModel::class.java)

        recyclerView?.apply {
            adapter = aboutAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        val list = listOf<BaseDataSourceItem<*>>(
            GenericListHeaderSourceItem(
                "About"
            ),

            GenericListItem(
                "Fehler melden",
                "Melden Sie Fehler oder schlagen Sie neue Funktionen vor.",
                R.drawable.ic_bug_report_black_24dp
            ).toSourceItem(this),


            GenericListItem(
                "Version",
                "v" + BuildConfig.VERSION_NAME,
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem()

            ,
            GenericListItem(
                "Changelog",
                "",
                R.drawable.ic_history_grey_700_24dp
            ).toSourceItem()

            ,
            GenericListItem(
                "Libraries",
                "",
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(),
            GenericListHeaderSourceItem(
                "Nanan"
            )

        )

        aboutAdapter.dataSource.setItems(list)


    }


}
