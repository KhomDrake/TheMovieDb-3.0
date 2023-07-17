package com.vlv.movie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.movie.R
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType

class HistoryData(
    val text: String,
    val type: HistoryType
) {
    constructor(history: History) : this(history.text, history.type)
}

class HistoryDiffUtil: DiffUtil.ItemCallback<HistoryData>() {
    override fun areContentsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areItemsTheSame(oldItem: HistoryData, newItem: HistoryData): Boolean {
        return oldItem.text == newItem.text
    }

}

class HistoryAdapter(
    private val onCLickItem: (HistoryData) -> Unit,
    private val onDelete: (HistoryData) -> Unit,
) : ListAdapter<HistoryData, HistoryViewHolder>(HistoryDiffUtil()) {

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = currentList[position]
        holder.bind(history)
        holder.itemView.setOnClickListener {
            onCLickItem.invoke(history)
        }
        holder.closeIcon.setOnClickListener {
            onDelete.invoke(history)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_history_item, parent, false)
        )
    }

}

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val text: AppCompatTextView by viewProvider(R.id.text)
    val closeIcon: AppCompatImageView by viewProvider(R.id.close_icon)

    fun bind(historyData: HistoryData) {
        text.text = historyData.text
    }

}