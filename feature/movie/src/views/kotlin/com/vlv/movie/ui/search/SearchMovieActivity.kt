package com.vlv.movie.ui.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.imperiya.ui.search.ImperiyaToolbarView
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.HistoryAdapter
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMovieActivity : AppCompatActivity(R.layout.search_activity) {

    private val search: ImperiyaSearchView by viewProvider(R.id.search)
    private val movies: RecyclerView by viewProvider(R.id.movies)
    private val historyTitle: AppCompatTextView by viewProvider(R.id.history_title)
    private val historyItems: RecyclerView by viewProvider(R.id.history_items)
    private val viewModel: SearchViewModel by viewModel()
    private val paginationAdapter = MoviePaginationAdapter()
    private val historyAdapter = HistoryAdapter(
        onCLickItem = {
            query(it.text, addToHistory = false)
        }, onDelete = {
            viewModel.deleteHistory(it.text)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSearch()
        setupHistory()
    }

    override fun onStart() {
        super.onStart()
        loadHistory()
    }

    private fun setupHistory() {
        historyItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        historyItems.adapter = historyAdapter
    }

    private fun loadHistory() {
        viewModel.searchHistory().observe(this) {
            historyAdapter.submitList(it)
        }
    }

    private fun setupSearch() {
        search.apply {
            onClickSearchListener {
                finishAfterTransition()
            }
            setHint("Search for movies")
            setup(
                onTextSubmit = { queryText ->
                    query(queryText)
                }
            )
        }

        movies.layoutManager = GridLayoutManager(this, 2)
        movies.adapter = paginationAdapter
    }

    private fun query(text: String?, addToHistory: Boolean = true) {
        lifecycleScope.launch {
            val queryNotNull = text ?: return@launch
            if(addToHistory) {
                viewModel.addHistory(queryNotNull)
            }
            viewModel.search(queryNotNull).distinctUntilChanged().apply {
                collectLatest {
                    paginationAdapter.submitData(it)
                }
            }
        }
    }

}