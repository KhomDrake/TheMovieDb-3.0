package com.vlv.themoviedb.ui.tv_show.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.ui.extension.loadUrl
import com.vlv.data.database.data.ImageType
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.themoviedb.R
import com.vlv.ui.R as RCommon

class TvShowItemDiff: ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.title == newItem.title
    }
}

class TvShowCarouselAdapter(
    private val onClick: (View, TvShow) -> Unit
) : ListAdapter<TvShow, TvShowViewHolder>(TvShowItemDiff()) {

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val series = currentList[position]
        holder.bind(series)
        holder.poster.setOnClickListener {
            onClick.invoke(holder.poster, series)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.tv_show_item,
                parent,
                false
            )
        )
    }

}

class TvShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val title: AppCompatTextView by viewProvider(R.id.series_title)

    fun bind(series: TvShow) {
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