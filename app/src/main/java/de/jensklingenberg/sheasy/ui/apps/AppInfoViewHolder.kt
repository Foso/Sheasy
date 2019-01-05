package de.jensklingenberg.sheasy.ui.apps

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*
import javax.inject.Inject

class AppInfoViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<AppInfoSourceItem>(viewParent, R.layout.list_item_generic) {

    @Inject
    lateinit var pm: PackageManager

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val appInfo = (item2 as AppInfoSourceItem).getPayload()

        appInfo?.let {
            itemView.apply {

                title.text = it.name
                caption.text = it.packageName
                icon.setImageDrawable(pm.getApplicationIcon(it.packageName))
                item.setOnClickListener {
                    item2.onEntryClickListener?.onItemClicked(appInfo)
                }
                moreBtn.visibility= View.VISIBLE
                moreBtn.setOnClickListener {
                    item2.onEntryClickListener?.onMoreButtonClicked(it,appInfo)
                }
            }
        }
    }
}