package com.vlv.common.ui.adapter.searchhistory

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

const val HISTORY_VIEW_TYPE = 23
const val HISTORY_TITLE_VIEW_TYPE = 24

class HistoryAdapter(
    private val title: String,
    private val onClick: (History) -> Unit,
    private val onClickClose: (History) -> Unit,
) : ListAdapter<History, ViewHolder>(HistoryDiffItem()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is HistoryTitleViewHolder -> {
                holder.bind(title)
            }
            is HistoryViewHolder -> {
                val item = currentList[position - 1]
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onClick.invoke(item)
                }
                holder.closeIcon.setOnClickListener {
                    onClickClose.invoke(item)
                }
            }
            else -> Unit
        }

    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if(itemCount > 0) itemCount + 1 else itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> HISTORY_TITLE_VIEW_TYPE
            else -> HISTORY_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            HISTORY_TITLE_VIEW_TYPE ->
                HistoryTitleViewHolder(parent.inflate(R.layout.common_history_title_item))
            else -> HistoryViewHolder(parent.inflate(R.layout.common_history_item))
        }
    }

}

class HistoryTitleViewHolder(view: View): ViewHolder(view) {

    fun bind(title: String) {
        (itemView as? AppCompatTextView)?.text = title
    }

}

class HistoryViewHolder(view: View): ViewHolder(view) {

    val closeIcon: AppCompatImageView by viewProvider(R.id.close_icon)
    private val title: AppCompatTextView by viewProvider(R.id.title)

    fun bind(history: History) {
        title.text = history.text
    }

}