package com.vlv.movie.ui.detail

import android.os.Bundle
import br.com.arch.toolkit.delegate.extraProvider
import coil.load
import com.google.android.material.tabs.TabLayout
import com.vlv.common.ui.DetailActivity
import com.vlv.extensions.toUrlMovieDb
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.adapter.DetailAdapter

class MovieDetailActivity : DetailActivity() {

    private val movie: Movie? by extraProvider("MOVIE", null)

    override val texts: List<String>
        get() = listOf(
            "About",
            "Cast",
            "Review"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = movie ?: return
        movie.backdropPath?.toUrlMovieDb()?.let {
            backdrop.load(it) {
                crossfade(1000)
            }
        }

        val adapter = DetailAdapter(texts, movie, supportFragmentManager)
        layout.adapter = adapter
        tabs.setupWithViewPager(layout)

        collapsing.title = movie.title
    }

    override fun onSelectedTab(tab: TabLayout.Tab?) {
        val position = tab?.position ?: return
        layout.setCurrentItem(position, true)
        layout.requestLayout()
    }

}