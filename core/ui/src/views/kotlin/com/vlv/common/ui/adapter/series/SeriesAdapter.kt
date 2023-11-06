package com.vlv.common.ui.adapter.series

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vlv.ui.R
import com.vlv.common.data.series.Series
import com.vlv.extensions.inflate

class SeriesAdapter(
    private val onClick: (Series, View) -> Unit
) : ListAdapter<Series, SeriesViewHolder>(SeriesDiffUtil()) {
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