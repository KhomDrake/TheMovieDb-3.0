package com.vlv.movie.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toMovie
import com.vlv.common.ui.DetailActivity
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.movie.R
import com.vlv.movie.ui.detail.adapter.DetailAdapter
import com.vlv.data.network.database.data.ImageType
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : DetailActivity() {

    private val viewModel: MovieDetailViewModel by viewModel()

    override val texts: List<String>
        get() = listOf(
            getString(R.string.movie_about_tab_title),
            getString(R.string.movie_cast_tab_title),
            getString(R.string.movie_review_tab_title),
            getString(R.string.movie_recommendation_tab_title)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = objDetail?.toMovie() ?: return
        movie.backdropPath?.let {
            it.loadUrl(backdrop, ImageType.BACKDROP)
        }
        movie.posterPath?.let {
            it.loadUrl(poster, ImageType.POSTER)
        }


        setupMenu(movie)

        val adapter = DetailAdapter(texts, movie, this)
        layout.adapter = adapter
        TabLayoutMediator(tabs, layout) { tab, position ->
            tab.text = texts[position]
        }.attach()

        collapsing.title = movie.title
    }

    private fun setupMenu(movie: Movie) {
        toolbar.inflateMenu(com.vlv.ui.R.menu.common_detail_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                com.vlv.ui.R.id.heart -> {
                    changeFavorite(movie)
                    true
                }
                else -> false
            }
        }
        updatedMenu()
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
                if (isFavorite) R.string.movie_is_favorite
                else R.string.movie_is_not_favorite
            )
            toolbar.findViewById<View?>(menuHeart.itemId)?.addAccessibilityDelegate(
                if (isFavorite) R.string.movie_remove_favorite
                else R.string.movie_add_favorite
            )
            menuHeart.icon = ContextCompat.getDrawable(
                this@MovieDetailActivity,
                if(isFavorite) com.vlv.imperiya.core.R.drawable.ic_heart_filled
                else com.vlv.imperiya.core.R.drawable.ic_heart_enable
            )
        }
    }

    private fun changeFavorite(movie: Movie) {
        viewModel.changeFavorite(movie).observe(this) {
            data { isFavorite ->
                toolbar.announceForAccessibility(
                    getString(
                        if (isFavorite) R.string.movie_added_to_favorite
                        else R.string.movie_removed_from_favorite
                    )
                )
                updatedMenuItem(isFavorite)
            }
        }
    }

}