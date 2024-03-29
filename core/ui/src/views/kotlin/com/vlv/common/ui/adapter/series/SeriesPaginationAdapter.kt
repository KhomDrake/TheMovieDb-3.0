package com.vlv.common.ui.adapter.series

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.ui.R

class SeriesDiffUtil: ItemCallback<TvShow>() {

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

}

const val VIEW_TYPE_SERIES = 43

class SeriesPaginationAdapter(
    private val onClick: (TvShow, View) -> Unit
): PagingDataAdapter<TvShow, RecyclerView.ViewHolder>(SeriesDiffUtil()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SeriesViewHolder -> {
                val data = getItem(position) ?: return
                holder.bind(data)
                holder.itemView.setOnClickListener {
                    onClick.invoke(data, it)
                }
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(itemCount == position || peek(position) == null) super.getItemViewType(position)
        else VIEW_TYPE_SERIES
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.common_series_pagination_item, parent, false)
        return SeriesViewHolder(view)
    }
}

class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val backdrop: AppCompatImageView by viewProvider(R.id.backdrop)
    private val title: AppCompatTextView by viewProvider(R.id.series_title)

    fun bind(data: TvShow) {
        backdrop.clipToOutline = true
        title.text = data.title
        backdrop.contentDescription =
            backdrop.context.getString(R.string.common_series_backdrop_content_description)
        backdrop.addAccessibilityDelegate(R.string.common_open_series_detail)
        data.posterPath.loadUrl(backdrop)
    }


}
