package de.jensklingenberg.sheasy.ui.share

import android.os.Bundle
import android.text.util.Linkify
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.text.util.LinkifyCompat
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class OutgoingMessageItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<OutgoingMessageSourceItem>(viewParent, R.layout.list_item_message) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val messageEvent = (sourceItem as OutgoingMessageSourceItem).getPayload()

        messageEvent?.let {
            itemView.apply {
                genericRL.setHorizontalGravity(Gravity.END)
                title.text = messageEvent.text
                LinkifyCompat.addLinks(title, Linkify.ALL)

                caption.text = messageEvent.time
                icon.setImageResource(R.drawable.ic_smartphone_black_24dp)

            }
        }
    }
}