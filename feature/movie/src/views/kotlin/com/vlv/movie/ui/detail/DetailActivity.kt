package com.vlv.movie.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import coil.load
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabView
import com.vlv.extensions.toUrlMovieDb
import com.vlv.movie.R
import com.vlv.movie.data.Movie

class DetailActivity : AppCompatActivity(R.layout.detail_activity) {

    private val backdrop: AppCompatImageView by viewProvider(R.id.path)
    private val tabs: TabLayout by viewProvider(R.id.tabs)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    private val movie: Movie? by extraProvider("MOVIE", null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie?.backdropPath?.toUrlMovieDb()?.let {
            backdrop.load(it) {
                crossfade(1000)
            }
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

}