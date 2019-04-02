package com.daveanthonythomas.technicalassessment.item.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.daveanthonythomas.technicalassessment.R
import com.daveanthonythomas.technicalassessment.item.model.Item

class ListItemAdapter : PagedListAdapter<Item, ListItemViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListItemViewHolder(parent.inflater.inflate(R.layout.list_item, parent, false))

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindTo(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }

    private val ViewGroup.inflater get() = LayoutInflater.from(this.context)
}