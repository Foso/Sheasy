package de.jensklingenberg.sheasy.ui.apps

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AndroidAppInfo
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class AppInfoViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<AppInfoSourceItem>(viewParent, R.layout.list_item_app) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val appInfo = (sourceItem as AppInfoSourceItem).getPayload()

        appInfo?.let {
            itemView.apply {

                title.text = it.name
                caption.text = it.packageName
                icon.setImageDrawable(appInfo.drawable)

                moreBtn.visibility = View.VISIBLE
                moreBtn.setOnClickListener {
                    sourceItem.onEntryClickListener?.onAppInfoMoreButtonClicked(it, appInfo)
                }
            }
        }
    }


    interface OnClick {
        fun onAppInfoMoreButtonClicked(it: View, appInfo: AndroidAppInfo)

    }
}