package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import kotlinx.android.synthetic.main.settings_toggle_entry.view.*

class GenericToggleItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<GenericToggleItemSourceItem>(viewParent, R.layout.settings_toggle_entry) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val listItem = (sourceItem as GenericToggleItemSourceItem).getPayload()

        listItem?.let {
            itemView.apply {
                itemTv.text = listItem.title
                toggleBtn.isChecked = listItem.checkedValue
                toggleBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                    listItem.onToggle(isChecked)

                }
            }
        }
    }


}