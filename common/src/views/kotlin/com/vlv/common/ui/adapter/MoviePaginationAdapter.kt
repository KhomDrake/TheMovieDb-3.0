package com.vlv.common.ui.adapter

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
import com.vlv.common.data.movie.Movie
import com.vlv.extensions.toUrlMovieDb

class MovieDiffUtil: ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }

}

const val VIEW_TYPE_MOVIE = 42

class MoviePaginationAdapter(
    private val onClickListener: (Movie, View) -> Unit
)
    : PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is MovieViewHolder -> {
                val data = getItem(position) ?: return
                holder.backdrop.setOnClickListener {
                    onClickListener.invoke(data, it)
                }
                holder.bind(data)
            }
            else -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(itemCount == position || peek(position) == null) super.getItemViewType(position)
            else VIEW_TYPE_MOVIE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.common_movie_pagination_item, parent, false)
        return MovieViewHolder(view)
    }

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val backdrop: AppCompatImageView by viewProvider(R.id.backdrop)
    private val title: AppCompatTextView by viewProvider(R.id.movie_title)

    fun bind(movie: Movie) {
        backdrop.clipToOutline = true
        title.text = movie.title
        backdrop.load(movie.posterPath?.toUrlMovieDb()) {
            crossfade(1000)
        }
    }

}