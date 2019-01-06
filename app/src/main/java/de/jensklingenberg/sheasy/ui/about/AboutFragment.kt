package de.jensklingenberg.sheasy.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListItem
import de.jensklingenberg.sheasy.ui.common.GenericListItemSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import kotlinx.android.synthetic.main.fragment_apps.*


class AboutFragment : BaseFragment(), OnEntryClickListener {

    private val aboutAdapter = BaseAdapter()
    lateinit var aboutViewModel: AboutViewModel


    /****************************************** Fragment Lifecycle methods  */

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)


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
                getString(R.string.app_name),
                "v" + BuildConfig.VERSION_NAME,
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem()

            ,
            GenericListItem(
                getString(R.string.About_Changelog),
                "",
                R.drawable.ic_history_grey_700_24dp
            ).toSourceItem(this)

            ,
            GenericListItem(
                getString(R.string.about_libraries),
                "",
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this)
            ,
            GenericListHeaderSourceItem(
                "License"
            ),
            GenericListItem(
                getString(R.string.about_License),
                getString(R.string.about_license_caption),
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this)


        )

        aboutAdapter.dataSource.setItems(list)


    }

    /****************************************** Listener methods  */

    override fun onMoreButtonClicked(view: View, payload: Any) {


    }

    override fun onItemClicked(payload: Any) {
        when (val item = payload) {

            is GenericListItemSourceItem -> {
                val genericListItem = item.getPayload()
                when (genericListItem?.title) {
                    getString(R.string.about_libraries) -> {
                        LibsBuilder()
                            .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                            .start(requireActivity())
                    }

                    getString(R.string.About_Changelog) -> {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.about_changelog_link))
                            )
                        startActivity(browserIntent)
                    }
                    getString(R.string.about_License) -> {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.about_license_link))
                            )
                        startActivity(browserIntent)
                    }
                }

            }
        }

    }



}
