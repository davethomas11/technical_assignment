package com.daveanthonythomas.technicalassessment.item.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.daveanthonythomas.technicalassessment.item.model.Item
import kotlinx.android.synthetic.main.list_item.view.*

class ListItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(item: Item) {
        view.item_text.text = item.name
    }
}