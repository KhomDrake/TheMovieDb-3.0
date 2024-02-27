package com.vlv.tv_show.ui.detail.about

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.AboutItemType
import com.vlv.common.ui.adapter.InformationViewHolder
import com.vlv.common.ui.adapter.PillAdapter
import com.vlv.common.ui.extension.loadUrl
import com.vlv.data.database.data.ImageType
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate
import com.vlv.extensions.setMargins
import com.vlv.tv_show.R
import com.vlv.ui.R as RCommon

class AboutItemDiffUtil: ItemCallback<AboutItem>() {

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
                    getDimension(com.vlv.imperiya.core.R.dimen.imperiya_carousel_m).toInt()
                holder.itemView.setMargins(left = marginHorizontal, right = marginHorizontal)
                (holder as? InformationViewHolder)?.bind(item.information)
            }
            is AboutItem.Title -> {
                (holder as? TitleViewHolder)?.bind(item)
            }
            is AboutItem.BigText -> {
                (holder as? BigTextViewHolder)?.bind(item)
            }
            is AboutItem.Episode -> {
                (holder as? EpisodeViewHolder)?.bind(item)
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
                TitleViewHolder(parent.inflate(RCommon.layout.common_title_about_item))
            AboutItemType.BIG_TEXT.ordinal ->
                BigTextViewHolder(parent.inflate(RCommon.layout.common_big_text_item))
            AboutItemType.EPISODE.ordinal ->
                EpisodeViewHolder(parent.inflate(R.layout.tv_show_episode_item))
            AboutItemType.GENRES.ordinal ->
                GenresViewHolder(parent.inflate(RCommon.layout.common_genres_item))
            AboutItemType.INFORMATION.ordinal ->
                InformationViewHolder(parent.inflate(RCommon.layout.common_information_item))
            else ->
                LineViewHolder(parent.inflate(RCommon.layout.common_line_item))
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

class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val episodeTitle: AppCompatTextView by viewProvider(R.id.episode_title)
    private val episodeNumberAndDate: AppCompatTextView by viewProvider(R.id.episode_number_and_date)

    fun bind(episodeItem: AboutItem.Episode) {
        poster.clipToOutline = true

        episodeItem.poster.loadUrl(poster, ImageType.POSTER)
        episodeTitle.text = episodeItem.title
        episodeNumberAndDate.text = episodeItem.description
    }

}