package com.vlv.movie.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.ImperiyaSearchView
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMovieActivity : AppCompatActivity(R.layout.search_activity) {

    private val search: ImperiyaSearchView by viewProvider(R.id.search)
    private val movies: RecyclerView by viewProvider(R.id.movies)
    private val viewModel: SearchViewModel by viewModel()
    private val paginationAdapter = MoviePaginationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        search.searchIcon {
            finishAfterTransition()
        }
        search.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    val queryNotNull = query ?: return@launch
                    viewModel.search(queryNotNull).distinctUntilChanged().apply {
                        collectLatest {
                            paginationAdapter.submitData(it)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        movies.layoutManager = GridLayoutManager(this, 2)
        movies.adapter = paginationAdapter


    }

}