package com.vlv.series.ui.adapter

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
import com.vlv.extensions.toUrlMovieDb
import com.vlv.series.R
import com.vlv.series.data.Series

class SeriesDiffUtil: ItemCallback<Series>() {

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.id == newItem.id
    }

}

class SeriesPaginationAdapter: PagingDataAdapter<Series, RecyclerView.ViewHolder>(SeriesDiffUtil()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SeriesViewHolder -> {
                val data = getItem(position) ?: return
                holder.bind(data)
            }
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.series_pagination_item, parent, false)
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
