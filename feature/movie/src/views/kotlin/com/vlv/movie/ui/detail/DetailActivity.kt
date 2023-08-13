package com.vlv.movie.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.extensions.toUrlMovieDb
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.adapter.DetailAdapter

class DetailActivity : AppCompatActivity(R.layout.detail_activity) {

    private val backdrop: AppCompatImageView by viewProvider(R.id.path)
    private val tabs: TabLayout by viewProvider(R.id.tabs)
    private val layout: ViewPager2 by viewProvider(R.id.layout)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    private val movie: Movie? by extraProvider("MOVIE", null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = movie ?: return
        movie.backdropPath?.toUrlMovieDb()?.let {
            backdrop.load(it) {
                crossfade(1000)
            }
        }

        val texts = listOf(
            "About",
            "Cast",
            "Review"
        )

        val adapter = DetailAdapter(movie, this)
        layout.adapter = adapter
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                layout.setCurrentItem(position, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })

        TabLayoutMediator(tabs, layout) { tab, position ->
            tab.text = texts[position]
        }.attach()

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}