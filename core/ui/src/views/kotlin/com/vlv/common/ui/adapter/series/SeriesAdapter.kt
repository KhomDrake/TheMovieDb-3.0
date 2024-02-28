package com.vlv.common.ui.adapter.series

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vlv.common.data.tv_show.TvShow
import com.vlv.extensions.inflate
import com.vlv.ui.R

class SeriesAdapter(
    private val onClick: (TvShow, View) -> Unit
) : ListAdapter<TvShow, SeriesViewHolder>(SeriesDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(parent.inflate(R.layout.common_series_pagination_item))
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick.invoke(data, it)
        }
    }
}