package com.vlv.common.ui.adapter.movie

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vlv.ui.R
import com.vlv.common.data.movie.Movie
import com.vlv.extensions.inflate

class MovieAdapter(
    private val onClickListener: (Movie, View) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(MovieDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.common_movie_pagination_item))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = currentList[position]
        holder.backdrop.setOnClickListener {
            onClickListener.invoke(data, it)
        }
        holder.bind(data)
    }
}