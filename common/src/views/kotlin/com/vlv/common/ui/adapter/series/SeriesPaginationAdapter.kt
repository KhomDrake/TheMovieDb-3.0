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
import coil.load
import com.vlv.common.R
import com.vlv.common.data.series.Series
import com.vlv.common.ui.extension.toUrlMovieDb

class SeriesDiffUtil: ItemCallback<Series>() {

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.id == newItem.id
    }

}

const val VIEW_TYPE_SERIES = 43

class SeriesPaginationAdapter(
    private val onClick: (Series, View) -> Unit
): PagingDataAdapter<Series, RecyclerView.ViewHolder>(SeriesDiffUtil()) {
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

    fun bind(data: Series) {
        backdrop.clipToOutline = true
        title.text = data.title
        backdrop.load(data.posterPath?.toUrlMovieDb()) {
            crossfade(1000)
        }
    }


}
