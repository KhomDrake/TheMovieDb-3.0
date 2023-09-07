package com.vlv.series.ui.detail

import android.os.Bundle
import coil.load
import com.vlv.common.ui.DetailActivity
import com.vlv.extensions.toUrlMovieDb
import com.vlv.series.data.toSeries
import com.vlv.series.ui.detail.adapter.DetailAdapter

class SeriesDetailActivity : DetailActivity() {

    override val texts: List<String>
        get() = listOf(
            "About",
            "Seasons",
            "Cast",
            "Reviews"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val series = objDetail?.toSeries() ?: return
        series.backdropPath?.toUrlMovieDb()?.let {
            backdrop.load(it) {
                crossfade(1000)
            }
        }

        val adapter = DetailAdapter(texts, series, supportFragmentManager)
        layout.adapter = adapter
        tabs.setupWithViewPager(layout)

        collapsing.title = series.title
    }


}