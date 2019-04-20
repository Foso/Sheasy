package de.jensklingenberg.sheasy.ui.common


class GenericToggleItemSourceItem(
    genericToggleItem: GenericToggleItem,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<GenericToggleItem>(GenericToggleItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<GenericToggleItem>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<GenericToggleItem>): Boolean {
        return false
    }


    init {
        setPayload(genericToggleItem)
    }


}