package com.vlv.movie.ui.detail.about.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.vlv.movie.R
import com.vlv.movie.data.Genre

class GenreDiffUtil: DiffUtil.ItemCallback<Genre>() {

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

}

class GenreAdapter : ListAdapter<Genre, GenreViewHolder>(GenreDiffUtil()) {

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_genre_item,
                parent,
                false
            )
        )
    }


}

class GenreViewHolder(view: View) : ViewHolder(view) {

    fun bind(genre: Genre) {
        (itemView as? AppCompatTextView)?.text = genre.name
    }

}