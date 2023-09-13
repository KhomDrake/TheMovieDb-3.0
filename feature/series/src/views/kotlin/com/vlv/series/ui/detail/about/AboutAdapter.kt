package com.vlv.series.ui.detail.about

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import coil.load
import com.vlv.common.ui.adapter.Information
import com.vlv.common.ui.adapter.InformationViewHolder
import com.vlv.common.ui.adapter.PillAdapter
import com.vlv.common.ui.adapter.PillItem
import com.vlv.extensions.inflate
import com.vlv.extensions.setMargins
import com.vlv.extensions.toUrlMovieDb
import com.vlv.series.R
import com.vlv.common.R as RCommon
import com.vlv.series.data.Episode
import kotlin.random.Random

enum class AboutItemType {
    LINE,
    TITLE,
    BIG_TEXT,
    EPISODE,
    INFORMATION,
    GENRES
}

sealed class AboutItem(
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE),
    val type: AboutItemType
) {

    class Line: AboutItem(type = AboutItemType.LINE)

    class Title(@StringRes val text: Int) : AboutItem(type = AboutItemType.TITLE)

    class BigText(val text: String) : AboutItem(type = AboutItemType.BIG_TEXT)

    class EpisodeItem(
        val episode: Episode
    ) : AboutItem(type = AboutItemType.EPISODE)

    class InformationItem(
        val information: Information
    ) : AboutItem(type = AboutItemType.INFORMATION)

    class Genres(
        val pillItems: List<PillItem>
    ) : AboutItem(type = AboutItemType.GENRES)

}

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
        val item = currentList[position]
        when(item) {
            is AboutItem.InformationItem -> {
                val marginHorizontal = holder.itemView.context.resources.
                    getDimension(com.vlv.imperiya.R.dimen.imperiya_carousel_m).toInt()
                holder.itemView.setMargins(left = marginHorizontal, right = marginHorizontal)
                (holder as? InformationViewHolder)?.bind(item.information)
            }
            is AboutItem.Title -> {
                (holder as? TitleViewHolder)?.bind(item)
            }
            is AboutItem.BigText -> {
                (holder as? BigTextViewHolder)?.bind(item)
            }
            is AboutItem.EpisodeItem -> {
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
                TitleViewHolder(parent.inflate(R.layout.series_title_item))
            AboutItemType.BIG_TEXT.ordinal ->
                BigTextViewHolder(parent.inflate(R.layout.series_big_text_item))
            AboutItemType.EPISODE.ordinal ->
                EpisodeViewHolder(parent.inflate(R.layout.series_episode_item))
            AboutItemType.GENRES.ordinal ->
                GenresViewHolder(parent.inflate(R.layout.series_genres_item))
            AboutItemType.INFORMATION.ordinal ->
                InformationViewHolder(parent.inflate(RCommon.layout.common_information_item))
            else ->
                LineViewHolder(parent.inflate(R.layout.series_line_item))
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
            text = titleText
        }
    }

}

class BigTextViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(bigText: AboutItem.BigText) {
        (itemView as? AppCompatTextView)?.text = bigText.text
    }

}

class EpisodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val episodeTitle: AppCompatTextView by viewProvider(R.id.episode_title)
    private val episodeNumberAndDate: AppCompatTextView by viewProvider(R.id.episode_number_and_date)

    fun bind(episodeItem: AboutItem.EpisodeItem) {
        poster.clipToOutline = true
        episodeItem.episode.stillPath?.toUrlMovieDb()?.let {
            poster.load(it) {
                crossfade(1000)
            }
        }
        episodeTitle.text = episodeItem.episode.name
        episodeNumberAndDate.text = episodeItem.episode.episodeNumberAndDate
    }

}