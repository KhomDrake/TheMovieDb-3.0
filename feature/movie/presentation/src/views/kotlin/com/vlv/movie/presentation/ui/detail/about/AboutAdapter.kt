package com.vlv.movie.presentation.ui.detail.about

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.data.about.AboutItem
import com.vlv.common.ui.adapter.InformationViewHolder
import com.vlv.common.ui.adapter.PillAdapter
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate
import com.vlv.extensions.setMargins
import com.vlv.ui.R

enum class AboutItemType {
    LINE,
    TITLE,
    BIG_TEXT,
    INFORMATION,
    GENRES
}

class AboutItemDiffUtil: DiffUtil.ItemCallback<AboutItem>() {

    override fun areContentsTheSame(oldItem: AboutItem, newItem: AboutItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: AboutItem, newItem: AboutItem): Boolean {
        return oldItem.id == newItem.id
    }

}

class AboutAdapter : ListAdapter<AboutItem, RecyclerView.ViewHolder>(AboutItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = currentList[position]) {
            is AboutItem.InformationItem -> {
                val marginHorizontal = holder.itemView.context.resources.
                getDimension(R.dimen.imperiya_carousel_m).toInt()
                holder.itemView.setMargins(left = marginHorizontal, right = marginHorizontal)
                (holder as? InformationViewHolder)?.bind(item.information)
            }
            is AboutItem.Title -> {
                (holder as? TitleViewHolder)?.bind(item)
            }
            is AboutItem.BigText -> {
                (holder as? BigTextViewHolder)?.bind(item)
            }
            is AboutItem.Genres -> {
                (holder as? GenresViewHolder)?.bind(item)
            }
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            AboutItemType.TITLE.ordinal ->
                TitleViewHolder(parent.inflate(R.layout.common_title_about_item))
            AboutItemType.BIG_TEXT.ordinal ->
                BigTextViewHolder(parent.inflate(R.layout.common_big_text_item))
            AboutItemType.GENRES.ordinal ->
                GenresViewHolder(parent.inflate(R.layout.common_genres_item))
            AboutItemType.INFORMATION.ordinal ->
                InformationViewHolder(parent.inflate(R.layout.common_information_item))
            else ->
                LineViewHolder(parent.inflate(R.layout.common_line_item))
        }
    }

}

class LineViewHolder(view: View) : RecyclerView.ViewHolder(view)

class GenresViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(genres: AboutItem.Genres) {
        (itemView as? RecyclerView)?.apply {
            layoutManager = LinearLayoutManager(
                this.context, LinearLayoutManager.HORIZONTAL, false
            )
            val pillAdapter = PillAdapter()
            adapter = pillAdapter
            pillAdapter.submitList(genres.pillItems)
        }
    }

}

class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(title: AboutItem.Title) {
        (itemView as? AppCompatTextView)?.apply {
            val titleText = context.getString(title.text)
            addHeadingAccessibilityDelegate()
            text = titleText
        }
    }

}

class BigTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(bigText: AboutItem.BigText) {
        (itemView as? AppCompatTextView)?.apply {
            contentDescription = bigText.description ?: bigText.text
            text = bigText.text
        }
    }

}
