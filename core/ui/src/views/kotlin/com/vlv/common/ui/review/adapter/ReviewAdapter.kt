package com.vlv.common.ui.review.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.ui.R
import com.vlv.common.data.review.Review
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate
import com.vlv.data.network.database.data.ImageType

class ReviewDiffUtil: DiffUtil.ItemCallback<Review>() {
    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.author == newItem.author
    }

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }
}

private const val VIEW_TYPE_REVIEW_TITLE = 0
private const val VIEW_TYPE_REVIEW_ITEM = 1

class ReviewAdapter: ListAdapter<Review, RecyclerView.ViewHolder>(ReviewDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> VIEW_TYPE_REVIEW_TITLE
            else -> VIEW_TYPE_REVIEW_ITEM
        }
    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if(itemCount > 0) itemCount + 1 else itemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_REVIEW_TITLE -> ReviewTitleViewHolder(
                parent.inflate(R.layout.common_title_item)
            )
            else -> ReviewViewHolder(
                parent.inflate(R.layout.common_review_item)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ReviewViewHolder -> {
                holder.bind(currentList[position - 1])
            }
            is ReviewTitleViewHolder -> {
                holder.bind(currentList.size)
            }
            else -> Unit
        }
    }
}

class ReviewTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(viewCount: Int) {
        (itemView as? AppCompatTextView)?.apply {
            text = context.getString(R.string.common_reviews_title, viewCount)
            addHeadingAccessibilityDelegate()
        }
    }

}

class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: AppCompatImageView by viewProvider(R.id.avatar)
    private val personName: AppCompatTextView by viewProvider(R.id.person_name)
    private val date: AppCompatTextView by viewProvider(R.id.date)
    private val text: AppCompatTextView by viewProvider(R.id.text)

    fun bind(review: Review) {
        review.url.loadUrl(avatar, ImageType.PROFILE)
        val context = itemView.context
        avatar.clipToOutline = true
        personName.text = review.author
        personName.contentDescription = context.getString(R.string.common_review_person_description, review.author)
        date.text = review.createdAt
        date.contentDescription = context.getString(R.string.common_review_date_description, review.createdAt)
        text.text = review.content
    }

}
