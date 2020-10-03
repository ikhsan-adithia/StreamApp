package com.stream.app.models

import android.content.Context
import com.stream.app.R
import com.stream.app.toast
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.notif_items.view.*

class NotifItems(
    val notif: DataItem,
    val context: Context
): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.notif_items
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.notif_text_Item.text = notif.message
        viewHolder.itemView.notif_time_Item.text = notif.date

        viewHolder.itemView.notif_text_Item.setOnClickListener {
            context.toast(notif.message.toString())
        }
    }

}