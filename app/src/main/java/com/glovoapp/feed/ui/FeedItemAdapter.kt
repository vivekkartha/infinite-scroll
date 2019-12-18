package com.glovoapp.feed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glovoapp.feed.R
import com.glovoapp.feed.data.FeedItem
import kotlinx.android.synthetic.main.item.view.*
import java.util.Date

class FeedItemAdapter(
    var items: List<FeedItem> = arrayListOf(),
    var onBottomReached: (lastDate: Date) -> Unit
) :
    RecyclerView.Adapter<FeedItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
        if (position == items.size - 1) {
            onBottomReached.invoke(items[position].createdAt)
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: FeedItem) = with(itemView) {
            title.text = item.title
        }
    }
}