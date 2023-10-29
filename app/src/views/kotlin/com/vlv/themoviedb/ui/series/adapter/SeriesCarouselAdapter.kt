package com.vlv.themoviedb.ui.series.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.series.Series
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.network.database.data.ImageType
import com.vlv.themoviedb.R
import com.vlv.common.R as RCommon

class SeriesItemDiff: ItemCallback<Series>() {
    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.title == newItem.title
    }
}

class SeriesCarouselAdapter(
    private val onClick: (View, Series) -> Unit
) : ListAdapter<Series, SeriesViewHolder>(SeriesItemDiff()) {

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = currentList[position]
        holder.bind(series)
        holder.poster.setOnClickListener {
            onClick.invoke(holder.poster, series)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.series_item,
                parent,
                false
            )
        )
    }

}

class SeriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val title: AppCompatTextView by viewProvider(R.id.series_title)

    fun bind(series: Series) {
        poster.clipToOutline = true
        title.text = series.title
        poster.contentDescription =
            poster.context.getString(RCommon.string.common_series_poster_content_description)
        poster.addAccessibilityDelegate(
            RCommon.string.common_open_series_detail
        )
        series.backdropPath.loadUrl(poster, ImageType.POSTER)
    }

}