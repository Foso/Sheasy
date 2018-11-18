package de.jensklingenberg.sheasy.ui.apps

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : BaseFragment() {

    private val appsAdapter = AppsAdapter()
    lateinit var appsViewModel: AppsViewModel

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_apps


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appsViewModel = obtainViewModel(AppsViewModel::class.java)

        recyclerView?.apply {
            adapter = appsAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        appsAdapter.setItems(appsViewModel.loadApps())
    }
}
