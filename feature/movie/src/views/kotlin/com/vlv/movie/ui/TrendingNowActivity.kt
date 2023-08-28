package com.vlv.movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingNowActivity : AppCompatActivity(R.layout.movie_trending_now_activity) {

    private val viewModel: TrendingNowViewModel by viewModel()

    private val movies: RecyclerView by viewProvider(R.id.movies)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        movies.layoutManager = GridLayoutManager(this, 2)
        val adapter = MoviePaginationAdapter()
        movies.adapter = adapter

        lifecycleScope.launch {
            viewModel.trendingNow().distinctUntilChanged().apply {
                collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

}