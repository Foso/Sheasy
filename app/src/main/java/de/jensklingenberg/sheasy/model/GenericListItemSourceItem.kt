package de.jensklingenberg.sheasy.model

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListItemViewHolder
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener


class GenericListItemSourceItem(
    genericListItem: GenericListItem,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<GenericListItem>(GenericListItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<GenericListItem>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<GenericListItem>): Boolean {
        return false
    }


    init {
        setPayload(genericListItem)
    }


}