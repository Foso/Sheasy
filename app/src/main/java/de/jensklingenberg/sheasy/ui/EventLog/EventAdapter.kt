package de.jensklingenberg.sheasy.ui.EventLog


import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter
import kotlinx.android.synthetic.main.list_item_event.view.*

/**
 * Created by jens on 25/2/18.
 */
class EventAdapter : SimpleRvAdapter() {
    private val list = ArrayList<Event>()
    var onDocsItemClickListener: OnTagClickListener? = null

    fun setItems(tagArrayList: List<Event>) {
        this.list.clear()
        this.list.addAll(tagArrayList)
    }

    override fun getLayoutId() = R.layout.list_item_event

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]

        holder.itemView.eventName.text = item.category.title
        holder.itemView.eventText.text = item.text
        holder.itemView.setOnClickListener { onDocsItemClickListener?.onTagClicked(item) }

    }

    override fun getItemCount() = list.size


    interface OnTagClickListener {
        fun onTagClicked(tag: Event)
    }


}