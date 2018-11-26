package de.jensklingenberg.sheasy.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class SimpleRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    open fun getLayoutId(): Int = throw NotImplementedError()

    class DefaultTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (viewType) {
            0 -> {
                val itemView = inflater.inflate(getLayoutId(), parent, false)

                return DefaultTagViewHolder(
                    itemView
                )
            }
        }

        throw RuntimeException(
            "there is no type that matches the type "
                    + viewType
                    + " + make sure your using types correctly"
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is DefaultTagViewHolder) {
            setupDefaultTagViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }


    open fun setupDefaultTagViewHolder(
        holder: DefaultTagViewHolder,
        position: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}