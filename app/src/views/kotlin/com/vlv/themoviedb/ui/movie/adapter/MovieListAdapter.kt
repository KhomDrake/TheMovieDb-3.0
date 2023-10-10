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
import com.vlv.common.data.movie.Movie
import com.vlv.common.ui.extension.toUrlMovieDb
import com.vlv.network.database.data.ImageType
import com.vlv.themoviedb.R
import com.vlv.themoviedb.TheMovieDb

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
        holder.itemView.setOnClickListener {
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
        poster.load(movie.backdropPath?.toUrlMovieDb(ImageType.BACKDROP)) {
            crossfade(1000)
        }
    }

}