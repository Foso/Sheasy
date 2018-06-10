package de.jensklingenberg.sheasy.ui.filemanager


import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter
import kotlinx.android.synthetic.main.list_item_event.view.*

/**
 * Created by jens on 25/2/18.
 */
class FilesAdapter : SimpleRvAdapter() {
    private val list = ArrayList<FileResponse>()

    var onEntryClickListener: OnEntryClickListener? = null


    fun setItems(tagArrayList: List<FileResponse>) {

        this.list.clear()
        this.list.addAll(tagArrayList)
        notifyDataSetChanged()
    }

    override fun getLayoutId() = R.layout.list_item_file

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.name
        holder.itemView.eventText.text = item.path
        holder.itemView.setOnClickListener { onEntryClickListener?.onTagClicked(item.path) }

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnEntryClickListener {
        fun onTagClicked(filePath: String)
    }


}