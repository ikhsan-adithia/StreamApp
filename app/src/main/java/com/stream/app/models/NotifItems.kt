package com.stream.app.models

import com.stream.app.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.notif_items.view.*

class NotifItems(
    val notif: DummyNotifModel
//    val context: Context
): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.notif_items
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.notif_text_Item.text = notif.text
        viewHolder.itemView.notif_time_Item.text = notif.time
    }

}