package com.vlv.movie.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.vlv.extensions.toUrlMovieDb
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.AutoMeasureViewPager
import com.vlv.movie.ui.detail.adapter.DetailAdapter
import java.lang.ref.WeakReference
import kotlin.math.abs

class DetailActivity : AppCompatActivity(R.layout.movie_detail_activity) {

    private val backdrop: AppCompatImageView by viewProvider(R.id.path)
    private val tabs: TabLayout by viewProvider(R.id.tabs)
    private val layout: AutoMeasureViewPager by viewProvider(R.id.layout)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val collapsing: CollapsingToolbarLayout by viewProvider(R.id.collapsing)
    private val appBar: AppBarLayout by viewProvider(R.id.appBarLayout)

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

        val adapter = DetailAdapter(texts, movie, supportFragmentManager)
        layout.adapter = adapter
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                layout.setCurrentItem(position, true)
                layout.requestLayout()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })

        tabs.setupWithViewPager(layout)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        collapsing.title = movie.title

        appBar.addOnOffsetChangedListener(
            AppBarStateChangeListener(WeakReference(toolbar)
        ) { layout, state ->
            val resource = when(state) {
                AppBarState.COLLAPSED -> {
                    com.vlv.imperiya.R.color.imperiya_color_primary
                }
                AppBarState.FULL_COLLAPSED -> {
                    com.vlv.imperiya.R.color.imperiya_color_background
                }
                AppBarState.EXPANDED, AppBarState.IDLE, AppBarState.COLLAPSING -> {
                    com.vlv.imperiya.R.color.imperiya_color_transparent
                }
            }

            appBar.setStatusBarForegroundResource(resource)
            collapsing.setStatusBarScrimResource(resource)
        })
    }

}

enum class AppBarState {
    EXPANDED,
    COLLAPSED,
    COLLAPSING,
    FULL_COLLAPSED,
    IDLE
}

class AppBarStateChangeListener(
    private val toolbar: WeakReference<Toolbar>,
    private val onStateChanged: (AppBarLayout, AppBarState) -> Unit
) : AppBarLayout.OnOffsetChangedListener {

    private var currentState = AppBarState.IDLE
    private var toolbarSize = -1

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val absVerticalOffSet = abs(verticalOffset)

        toolbarSize = toolbar.get()?.measuredHeight ?: 0

        val collapsedLimit = (appBarLayout.totalScrollRange - toolbarSize)

        val newState = when (absVerticalOffSet) {
            0 -> {
                AppBarState.EXPANDED;
            }
            collapsedLimit -> {
                AppBarState.COLLAPSED
            }
            appBarLayout.totalScrollRange -> {
                AppBarState.FULL_COLLAPSED
            }
            else -> {
                AppBarState.COLLAPSING
            }
        }

        if(currentState != newState) {
            currentState = newState
            onStateChanged(appBarLayout, newState);
        }
    }

}