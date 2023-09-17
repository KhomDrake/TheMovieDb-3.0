package com.vlv.movie.ui.search

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.MoviePaginationAdapter
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.extensions.*
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.movie.R
import com.vlv.common.R as RCommon
import com.vlv.movie.ui.adapter.HistoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SEARCH_STATE = 23

class SearchMovieActivity : AppCompatActivity(R.layout.movie_search_activity) {

    private val root: ViewGroup by viewProvider(R.id.root)
    private val search: ImperiyaSearchView by viewProvider(R.id.search)
    private val movies: RecyclerView by viewProvider(R.id.movies)
    private val empty: AppCompatTextView by viewProvider(R.id.empty)
    private val error: AppCompatTextView by viewProvider(R.id.error)
    private val loading: AppCompatTextView by viewProvider(R.id.loading)
    private val historyTitle: AppCompatTextView by viewProvider(R.id.history_title)
    private val historyItems: RecyclerView by viewProvider(R.id.history_items)
    private val viewModel: SearchViewModel by viewModel()
    private val paginationAdapter = MoviePaginationAdapter { movie, view ->
        val intent = toMovieDetail(movie.toDetailObject())
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                getString(RCommon.string.common_poster_transition_name)
            ).toBundle()
        )
    }
    private val historyAdapter = HistoryAdapter(
        onCLickItem = {
            search.setText(it.text)
            query(it.text, addToHistory = false)
        }, onDelete = {
            viewModel.deleteHistory(it.text)
        }
    )
    private val viewStateMachine = ViewStateMachine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewStateMachine()
        setupSearch()
        setupHistory()
        setupMovies()
    }

    override fun onStart() {
        super.onStart()
        loadHistory()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = SEARCH_STATE
                setOnChangeState {
                    TransitionManager.beginDelayedTransition(root)
                }
            }

            state(SEARCH_STATE) {
                visibles(historyItems, historyTitle)
                gones(movies, empty, error, loading)
            }

            stateData {
                visibles(movies)
                gones(historyItems, historyTitle, empty, error, loading)
            }

            stateEmpty {
                visibles(empty, historyItems, historyTitle)
                gones(error, loading, movies)
            }

            stateLoading {
                visibles(loading, historyItems, historyTitle)
                gones(empty, error, movies)
            }

            stateError {
                visibles(error, historyItems, historyTitle)
                gones(empty, loading, movies)
            }
        }
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

    private fun setupMovies() {
        movies.layoutManager = GridLayoutManager(this, 2)
        movies.adapter = paginationAdapter
        paginationAdapter.setupState(
            onData = {
                viewStateMachine.dataState()
            },
            onError = {
                viewStateMachine.errorState()
            },
            onEmpty = {
                viewStateMachine.emptyState()
            },
            onLoading = {
                viewStateMachine.loadingState()
            }
        )
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
            onClickCloseListener {
                lifecycleScope.launch {
                    search.clearText()
                    viewStateMachine.changeState(SEARCH_STATE)
                }
            }
        }
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

    override fun onDestroy() {
        viewStateMachine.shutdown()
        super.onDestroy()
    }

}