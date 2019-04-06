package de.jensklingenberg.sheasy.ui.common


data class NoOrEmptyContentItem(val message: String = "") {
    fun toSourceItem(): BaseDataSourceItem<*>? {
        return NoOrEmptyContentSourceItem(this)

    }
}

class NoOrEmptyContentSourceItem(
    genericToggleItem: NoOrEmptyContentItem,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<NoOrEmptyContentItem>(NoOrEmtpyItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<NoOrEmptyContentItem>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<NoOrEmptyContentItem>): Boolean {
        return false
    }


    init {
        setPayload(genericToggleItem)
    }


}