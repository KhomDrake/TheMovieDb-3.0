package com.vlv.series.ui.detail.season

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import coil.load
import com.vlv.extensions.inflate
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.common.ui.extension.toUrlMovieDb
import com.vlv.network.data.series.Season
import com.vlv.series.R

class SeasonDiffUtil: ItemCallback<Season>() {
    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem.id == newItem.id
    }
}

class SeasonAdapter : ListAdapter<Season, SeasonViewHolder>(SeasonDiffUtil()) {

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(parent.inflate(R.layout.series_season_item))
    }

}

class SeasonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val poster: AppCompatImageView by viewProvider(R.id.poster)
    private val title: AppCompatTextView by viewProvider(R.id.season_title)
    private val numberAndDate: AppCompatTextView
        by viewProvider(R.id.season_number_of_episodes_and_date)


    fun bind(season: Season) {
        itemView.clipToOutline = true
        title.text = season.name

        poster.clipToOutline = true

        season.posterPath?.toUrlMovieDb()?.let {
            poster.load(it) {
                crossfade(1000)
            }
        }

        numberAndDate.text = "${season.episodeCount} episodes - ${season.airDate?.toFormattedString(patternDate2())}"
    }

}
