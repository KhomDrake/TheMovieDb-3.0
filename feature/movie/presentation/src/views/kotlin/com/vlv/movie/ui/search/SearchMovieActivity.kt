package com.vlv.movie.ui.search

import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.movie.MoviePaginationAdapter
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.common.ui.search.SearchActivity
import com.vlv.extensions.*
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.movie.R
import com.vlv.data.network.database.data.History
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.vlv.ui.R as RCommon

class SearchMovieActivity : SearchActivity() {

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
    override val loadingLayout: Int
        get() = RCommon.layout.common_listing_movie_loading
    override val titleHistoryTitle: Int
        get() = R.string.movie_search_history_title
    override val searchHint: Int
        get() = R.string.movie_search_hint
    override val adapter: PagingDataAdapter<*, *>
        get() = paginationAdapter

    override fun onTextSubmit(query: String?) {
        lifecycleScope.launch {
            val queryNotNull = query ?: return@launch
            viewModel.searchMovie(queryNotNull).distinctUntilChanged().apply {
                collectLatest {
                    paginationAdapter.submitData(it)
                }
            }
        }
    }

    override fun addHistory(text: String?) {
        text?.let {
            viewModel.addToHistory(it)
        }
    }

    override fun removeHistory(history: History) {
        viewModel.removeHistory(history)
    }

    override fun loadHistory() {
        viewModel.historyBySearchType(getString(titleHistoryTitle)).observe(this) {
            updateHistory(it)
        }
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 2)
    }

    override fun loaderAdapter(): LoaderAdapter {
        return MovieLoaderAdapter {
            paginationAdapter.retry()
        }
    }

    override fun configStateView(stateView: StateView) {
        stateView
            .setStateIcon(com.vlv.imperiya.core.R.drawable.ic_movie)
            .setTitle(R.string.movie_search_empty_state)
    }

    override fun configWarningView(smallWarningView: SmallWarningView) = Unit

}