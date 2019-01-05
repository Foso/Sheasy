package de.jensklingenberg.sheasy.ui.common


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