package de.jensklingenberg.sheasy.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<P : BaseDataSourceItem<*>>(viewParent: ViewGroup, @LayoutRes layoutId: Int) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(viewParent.context).inflate(
            layoutId,
            viewParent,
            false
        )
    ) {

    protected var context: Context

    init {
        context = viewParent.context
    }

    fun onBindViewHolderInternal(item: Any, payloads: List<Any>) {
        var diff = Bundle()
        if (payloads.isNotEmpty()) {
            diff = payloads[0] as Bundle
        }
        onBindViewHolder(item, diff)
    }

    abstract fun onBindViewHolder(item: Any, diff: Bundle)
}
