package de.jensklingenberg.sheasy.ui.filemanager

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.FileResponse


import kotlinx.android.synthetic.main.list_item_event.view.*

/**
 * Created by jens on 25/2/18.
 */
class FilesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<FileResponse>()

    fun setItems(tagArrayList: List<FileResponse>) {
this.list.clear()
        this.list.addAll(tagArrayList)
    }

    class DefaultTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (viewType) {
            0 -> {
                val itemView = inflater.inflate(R.layout.list_item_file, parent, false)

                return DefaultTagViewHolder(
                    itemView
                )
            }
        }

        throw RuntimeException("there is no type that matches the type "
                + viewType
                + " + make sure your using types correctly")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is DefaultTagViewHolder) {
            setupDefaultTagViewHolder(holder, position)
        }
    }

    private fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.name
        holder.itemView.eventText.text = item.path
        //holder.itemView.setOnClickListener { onDocsItemClickListener.onTagClicked(item) }

    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnTagClickListener {
        fun onTagClicked(tag: Event)
    }


}