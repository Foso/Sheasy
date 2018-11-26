package de.jensklingenberg.sheasy.model

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListHeaderViewHolder
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener


class GenericListHeaderSourceItem(
    genericListItem: GenericListHeaderTitle,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<GenericListHeaderTitle>(GenericListHeaderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<GenericListHeaderTitle>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<GenericListHeaderTitle>): Boolean {
        return false
    }


    init {
        setPayload(genericListItem)
    }


}