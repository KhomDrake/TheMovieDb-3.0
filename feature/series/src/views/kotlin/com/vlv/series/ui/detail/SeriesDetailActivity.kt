package com.vlv.series.ui.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.common.data.movie.toMovie
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.toSeries
import com.vlv.common.ui.DetailActivity
import com.vlv.common.ui.extension.toUrlMovieDb
import com.vlv.series.R
import com.vlv.series.ui.detail.adapter.DetailAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesDetailActivity : DetailActivity() {

    private val viewModel: SeriesDetailViewModel by viewModel()

    override val texts: List<String>
        get() = listOf(
            getString(R.string.series_about_tab_title),
            getString(R.string.series_seasons_tab_title),
            getString(R.string.series_cast_tab_title),
            getString(R.string.series_review_tab_title),
            getString(R.string.series_recommendation_tab_title)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val series = objDetail?.toSeries() ?: return
        series.backdropPath?.toUrlMovieDb()?.let {
            backdrop.load(it) {
                crossfade(1000)
            }
        }
        series.posterPath?.toUrlMovieDb()?.let {
            poster.load(it) {
                crossfade(1000)
            }
        }

        toolbar.inflateMenu(com.vlv.common.R.menu.common_detail_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                com.vlv.common.R.id.heart -> {
                    changeFavorite(series)
                    true
                }
                else -> false
            }
        }
        updatedMenu()

        val adapter = DetailAdapter(texts, series, this)
        layout.adapter = adapter
        TabLayoutMediator(tabs, layout) { tab, position ->
            tab.text = texts[position]
        }.attach()

        collapsing.title = series.title
    }

    private fun updatedMenu() {
        val movie = objDetail?.toMovie() ?: return
        viewModel.getFavorite(movie.id).observe(this) {
            data {
                updatedMenuItem(isFavorite = it)
            }
        }
    }

    private fun updatedMenuItem(isFavorite : Boolean) {
        runCatching {
            val menuHeart = toolbar.menu?.getItem(0) ?: return@runCatching
            menuHeart.icon = ContextCompat.getDrawable(
                this@SeriesDetailActivity,
                if(isFavorite) com.vlv.imperiya.R.drawable.ic_heart_filled
                else com.vlv.imperiya.R.drawable.ic_heart_enable
            )
        }
    }

    private fun changeFavorite(series: Series) {
        viewModel.changeFavorite(series).observe(this) {
            data { isFavorite ->
                updatedMenuItem(isFavorite)
            }
        }
    }

}