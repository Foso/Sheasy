package de.jensklingenberg.sheasy.ui.common


class BaseDataSource internal constructor() : ArrayList<BaseDataSourceItem<*>>() {


    var emptyView : BaseDataSourceItem<*>?= null
        set(value) {
            field=value
            setItems(emptyList())
        }

    /**
     * a @[Map] of @[BaseViewHolder] mapped to viewTypes
     */
    private val viewTypes = HashMap<Class<out BaseViewHolder<*>>, Int>()


    override fun add(item: BaseDataSourceItem<*>): Boolean {
        if (!viewTypes.containsKey(item.viewTypeClass)) {

            viewTypes[item.viewTypeClass] = viewTypes.size
        }
        item.index = size
        return super.add(item)
    }

    override fun addAll(items: Collection<BaseDataSourceItem<*>>): Boolean {
        for (item in items) {
            add(item)
        }
        return true
    }

    fun setItems(items: List<BaseDataSourceItem<*>>) {
        if(items.isEmpty()){
            var newItems = items
            emptyView?.let {
                newItems= listOf(it)
            }
            this.clear()
            newItems.forEach { add(it) }

        }else{
            this.clear()
            items.forEach { add(it) }
        }


    }

    internal fun getViewTypeForPosition(position: Int): Int? {
        val item = get(position)
        return viewTypes[item.viewTypeClass]
    }


    internal fun getViewHolderClassForViewType(viewType: Int): Class<out BaseViewHolder<*>>? {
        for ((key, value) in viewTypes) {
            if (value == viewType) {
                return key
            }
        }
        return null
    }
}
