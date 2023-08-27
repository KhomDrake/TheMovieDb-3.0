package com.vlv.movie.ui.detail.review.adapter

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
import com.vlv.movie.data.Review

class ReviewDiffUtil: DiffUtil.ItemCallback<Review>() {
    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.author == newItem.author
    }

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }
}

class ReviewAdapter: ListAdapter<Review, ReviewViewHolder>(ReviewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_review_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: AppCompatImageView by viewProvider(R.id.avatar)
    private val personName: AppCompatTextView by viewProvider(R.id.person_name)
    private val date: AppCompatTextView by viewProvider(R.id.date)
    private val text: AppCompatTextView by viewProvider(R.id.text)

    fun bind(review: Review) {
        review.url?.toUrlMovieDb()?.let {
            avatar.load(it) {
                crossfade(1000)
            }
        }
        personName.text = review.author
        date.text = review.createdAt
        text.text = review.content
    }

}
