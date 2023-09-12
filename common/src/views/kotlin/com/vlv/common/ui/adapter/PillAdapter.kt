package com.vlv.common.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.R
import com.vlv.extensions.inflate

class PillItem(
    val id: Int,
    val title: String
)

class PillItemDiffUtil : DiffUtil.ItemCallback<PillItem>() {

    override fun areContentsTheSame(oldItem: PillItem, newItem: PillItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: PillItem, newItem: PillItem): Boolean {
        return oldItem.id == newItem.id
    }

}

class PillAdapter(
    val onClickItem: ((PillItem) -> Unit)? = null
) : ListAdapter<PillItem, PillViewHolder>(PillItemDiffUtil()) {

    override fun onBindViewHolder(holder: PillViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
        onClickItem?.let { onClick ->
            holder.itemView.setOnClickListener {
                onClick.invoke(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillViewHolder {
        return PillViewHolder(parent.inflate(R.layout.common_pill_item))
    }

}

class PillViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(pillItem: PillItem) {
        (itemView as? AppCompatTextView)?.text = pillItem.title
    }

}