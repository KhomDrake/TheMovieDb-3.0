package com.vlv.common.ui.adapter.searchhistory

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.data.database.data.History
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate
import com.vlv.ui.R

class HistoryDiffItem: DiffUtil.ItemCallback<HistoryItems>() {

    override fun areContentsTheSame(oldItem: HistoryItems, newItem: HistoryItems): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: HistoryItems, newItem: HistoryItems): Boolean {
        return oldItem.id == newItem.id
    }

}

const val HISTORY_VIEW_TYPE = 23
const val HISTORY_TITLE_VIEW_TYPE = 24

sealed class HistoryItems(val id: String) {
    class HistoryTitle(val title: String): HistoryItems(title)

    class HistoryItem(val data: History): HistoryItems(data.toString())
}

class HistoryAdapter(
    private val onClick: (History) -> Unit,
    private val onClickClose: (History) -> Unit,
) : ListAdapter<HistoryItems, ViewHolder>(HistoryDiffItem()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is HistoryTitleViewHolder -> {
                holder.bind((currentList[position] as HistoryItems.HistoryTitle).title)
            }
            is HistoryViewHolder -> {
                val item = (currentList[position] as HistoryItems.HistoryItem).data
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
        (itemView as? AppCompatTextView)?.apply {
            addHeadingAccessibilityDelegate()
            text = title
        }
    }

}

class HistoryViewHolder(view: View): ViewHolder(view) {

    val closeIcon: AppCompatImageView by viewProvider(R.id.close_icon)
    private val title: AppCompatTextView by viewProvider(R.id.small_warning_title)

    fun bind(history: History) {
        val description = closeIcon.context.getString(R.string.common_search_history_icon_content_description)
        closeIcon.contentDescription = description

        itemView.addAccessibilityDelegate(R.string.common_search_history_click_history)

        closeIcon.addAccessibilityDelegate(
            closeIcon.context.getString(R.string.common_search_history_icon_action_description),
            description
        )
        title.text = history.text
    }

}