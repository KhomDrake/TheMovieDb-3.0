package com.vlv.themoviedb.ui.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.movie.Movie
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.network.database.data.ImageType
import com.vlv.themoviedb.R
import com.vlv.ui.R as RCommon

class MovieDiffUtil: DiffUtil.ItemCallback<Movie>() {

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.posterPath && oldItem.posterPath == newItem.posterPath
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

}

class MoviesCarouselAdapter(
    private val onClickMovie: (View, Movie) -> Unit = { _, _ -> }
) : ListAdapter<Movie, MovieCarouselViewHolder>(MovieDiffUtil()) {

    override fun onBindViewHolder(holder: MovieCarouselViewHolder, position: Int) {
        val movie = currentList[position]
        holder.bind(movie)
        holder.poster.setOnClickListener {
            onClickMovie.invoke(holder.poster, movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent, false
        )
        return MovieCarouselViewHolder(view)
    }


}

class MovieCarouselViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val title: AppCompatTextView by viewProvider(R.id.movie_title)

    fun bind(movie: Movie) {
        poster.clipToOutline = true
        title.text = movie.title
        poster.contentDescription =
            poster.context.getString(RCommon.string.common_movie_poster_content_description)
        poster.addAccessibilityDelegate(
            RCommon.string.common_open_movie_detail
        )
        movie.backdropPath.loadUrl(poster, ImageType.BACKDROP)
    }

}