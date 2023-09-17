package com.vlv.movie.ui.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.common.data.movie.toMovie
import com.vlv.common.ui.DetailActivity
import com.vlv.extensions.toUrlMovieDb
import com.vlv.movie.R
import com.vlv.movie.ui.detail.adapter.DetailAdapter
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

        heart.setOnClickListener {
            viewModel.addToFavorite(movie).observe(this) {
                data {
                    heart.setImageDrawable(
                        ContextCompat.getDrawable(this@MovieDetailActivity, com.vlv.imperiya.R.drawable.ic_heart_filled)
                    )
                }
            }
        }

        movie.backdropPath?.toUrlMovieDb()?.let {
            backdrop.load(it) {
                crossfade(1000)
            }
        }
        movie.posterPath?.toUrlMovieDb()?.let {
            poster.load(it) {
                crossfade(1000)
            }
        }

        val adapter = DetailAdapter(texts, movie, this)
        layout.adapter = adapter
        TabLayoutMediator(tabs, layout) { tab, position ->
            tab.text = texts[position]
        }.attach()

        collapsing.title = movie.title
    }

}