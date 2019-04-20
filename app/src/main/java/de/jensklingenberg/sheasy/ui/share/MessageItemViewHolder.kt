package de.jensklingenberg.sheasy.ui.share

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class MessageItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<MessageSourceItem>(viewParent, R.layout.list_item_message) {


    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val messageEvent = (item as MessageSourceItem).getPayload()

        messageEvent?.let {
            itemView.apply {
                genericRL.setHorizontalGravity(Gravity.END)
                title.text = messageEvent.text
                caption.text = messageEvent.time
                icon.setImageResource(R.drawable.ic_smartphone_black_24dp)



            }
        }
    }
}