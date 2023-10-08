package com.vlv.common.ui.adapter.searchhistory

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.R
import com.vlv.extensions.inflate
import com.vlv.network.database.data.History

class HistoryDiffItem: DiffUtil.ItemCallback<History>() {

    override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
        return oldItem.type.ordinal == newItem.type.ordinal && oldItem.text == newItem.text
    }

}

class HistoryAdapter(
    private val onClick: (History) -> Unit,
    private val onClickClose: (History) -> Unit,
) : ListAdapter<History, HistoryViewHolder>(HistoryDiffItem()) {

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick.invoke(item)
        }
        holder.closeIcon.setOnClickListener {
            onClickClose.invoke(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(parent.inflate(R.layout.common_history_item))
    }

}

class HistoryViewHolder(view: View): ViewHolder(view) {

    val closeIcon: AppCompatImageView by viewProvider(R.id.close_icon)
    private val title: AppCompatTextView by viewProvider(R.id.title)

    fun bind(history: History) {
        title.text = history.text
    }

}