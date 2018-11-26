package de.jensklingenberg.sheasy.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {
    val dataSource = BaseDataSource()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewHolderClass = dataSource.getViewHolderClassForViewType(viewType)
        if (viewHolderClass != null) {
            return viewHolderClass.getDeclaredConstructor(ViewGroup::class.java)
                .newInstance(parent)
        }
        return throw Exception("NO Viewholder")

    }

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: List<Any>) {
        val item = dataSource[position]
        //determine, if the empty state should fit the height of the recycler view
        holder.onBindViewHolderInternal(item, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return dataSource.getViewTypeForPosition(position) ?: throw Exception("No viewType")
    }


}