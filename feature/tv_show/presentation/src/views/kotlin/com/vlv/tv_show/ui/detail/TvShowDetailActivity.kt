package com.vlv.tv_show.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.common.data.movie.toMovie
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.data.tv_show.toSeries
import com.vlv.common.ui.DetailActivity
import com.vlv.common.ui.extension.loadUrl
import com.vlv.data.database.data.ImageType
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.tv_show.R
import com.vlv.tv_show.ui.detail.adapter.DetailAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowDetailActivity : DetailActivity() {

    private val viewModel: TvShowDetailViewModel by viewModel()

    override val texts: List<String>
        get() = listOf(
            getString(R.string.tv_show_about_tab_title),
            getString(R.string.tv_show_seasons_tab_title),
            getString(R.string.tv_show_cast_tab_title),
            getString(R.string.tv_show_review_tab_title),
            getString(R.string.tv_show_recommendation_tab_title)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val series = objDetail?.toSeries() ?: return
        series.backdropPath?.let {
            it.loadUrl(backdrop, ImageType.BACKDROP)
        }
        series.posterPath?.let {
            it.loadUrl(poster, ImageType.POSTER)
        }

        toolbar.inflateMenu(com.vlv.ui.R.menu.common_detail_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                com.vlv.ui.R.id.heart -> {
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
            menuHeart.contentDescription = getString(
                if (isFavorite) R.string.tv_show_is_favorite
                else R.string.tv_show_is_not_favorite
            )
            toolbar.findViewById<View?>(menuHeart.itemId)?.addAccessibilityDelegate(
                if (isFavorite) R.string.tv_show_remove_favorite
                else R.string.tv_show_add_favorite
            )
            menuHeart.icon = ContextCompat.getDrawable(
                this@TvShowDetailActivity,
                if(isFavorite) com.vlv.imperiya.core.R.drawable.ic_heart_filled
                else com.vlv.imperiya.core.R.drawable.ic_heart_enable
            )
        }
    }

    private fun changeFavorite(series: TvShow) {
        viewModel.changeFavorite(series).observe(this) {
            data { isFavorite ->
                toolbar.announceForAccessibility(
                    getString(
                        if (isFavorite) R.string.tv_show_added_to_favorite
                        else R.string.tv_show_removed_from_favorite
                    )
                )
                updatedMenuItem(isFavorite)
            }
        }
    }

}