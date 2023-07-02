package com.vlv.movie.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.search.ImperiyaToolbarView
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMovieActivity : AppCompatActivity(R.layout.search_activity) {

    private val search: ImperiyaToolbarView by viewProvider(R.id.search)
    private val movies: RecyclerView by viewProvider(R.id.movies)
    private val viewModel: SearchViewModel by viewModel()
    private val paginationAdapter = MoviePaginationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search.setNavigationOnClickListener {
            finishAfterTransition()
        }
        search.searchView.apply {
            setHint("Search for movies")
            setup(
                onTextSubmit = { query ->
                    lifecycleScope.launch {
                        val queryNotNull = query ?: return@launch
                        viewModel.search(queryNotNull).distinctUntilChanged().apply {
                            collectLatest {
                                paginationAdapter.submitData(it)
                            }
                        }
                    }
                }
            )
        }

        movies.layoutManager = GridLayoutManager(this, 2)
        movies.adapter = paginationAdapter


    }

}