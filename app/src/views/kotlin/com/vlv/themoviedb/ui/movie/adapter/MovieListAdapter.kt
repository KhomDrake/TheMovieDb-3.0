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
import coil.load
import com.vlv.extensions.toUrlMovieDb
import com.vlv.movie.data.Movie
import com.vlv.themoviedb.R

class MovieDiffUtil: DiffUtil.ItemCallback<Movie>() {

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.posterPath && oldItem.posterPath == newItem.posterPath
    }

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

}

class MoviesCarouselAdapter : ListAdapter<Movie, MovieCarouselViewHolder>(MovieDiffUtil()) {

    override fun onBindViewHolder(holder: MovieCarouselViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent, false
        )
        return MovieCarouselViewHolder(view)
    }


}

class MovieCarouselViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val title: AppCompatTextView by viewProvider(R.id.movie_title)

    fun bind(movie: Movie) {
        poster.clipToOutline = true
        title.text = movie.title
        poster.load(movie.backdropPath?.toUrlMovieDb()) {
            crossfade(1000)
        }
    }

}