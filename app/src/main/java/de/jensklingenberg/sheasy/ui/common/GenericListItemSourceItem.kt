package de.jensklingenberg.sheasy.ui.common


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