package com.vlv.movie.ui.detail.cast.adapter

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
import com.vlv.movie.R
import com.vlv.movie.data.Cast

class CastDiffUtil: DiffUtil.ItemCallback<Cast>() {
    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.name == newItem.name && oldItem.character == newItem.character
    }

    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.castId == newItem.castId
    }
}

class CastAdapter: ListAdapter<Cast, CastViewHolder>(CastDiffUtil()) {

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_cast_item,
                parent,
                false
            )
        )
    }

}

class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: AppCompatImageView by viewProvider(R.id.avatar)
    private val personName: AppCompatTextView by viewProvider(R.id.person_name)
    private val character: AppCompatTextView by viewProvider(R.id.character)

    fun bind(cast: Cast) {
        avatar.clipToOutline = true
        cast.profilePath?.toUrlMovieDb()?.let {
            avatar.load(it) {
                crossfade(1000)
            }
        }
        personName.text = cast.name
        character.text = cast.character
    }

}