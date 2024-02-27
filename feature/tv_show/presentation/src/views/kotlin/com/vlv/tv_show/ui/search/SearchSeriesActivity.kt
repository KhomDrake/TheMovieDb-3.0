package com.vlv.tv_show.ui.search

import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.data.tv_show.toDetailObject
import com.vlv.common.route.toTvShowsDetail
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesLoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesPaginationAdapter
import com.vlv.common.ui.search.SearchActivity
import com.vlv.data.database.data.History
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.tv_show.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchSeriesActivity : SearchActivity() {

    private val viewModel: SearchViewModel by viewModel()

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    private val pagingAdapter = SeriesPaginationAdapter { series, view ->
        startActivity(
            toTvShowsDetail(series.toDetailObject()),
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                getString(com.vlv.ui.R.string.common_poster_transition_name)
            ).toBundle()
        )
    }

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_listing_series_loading

    override val searchHint: Int
        get() = R.string.tv_show_search_hint

    override val titleHistoryTitle: Int
        get() = R.string.tv_show_search_history_title

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 2)
    }

    override fun loadHistory() {
        viewModel.historyBySearchType(getString(titleHistoryTitle)).observe(this) {
            updateHistory(it)
        }
    }

    override fun loaderAdapter(): LoaderAdapter {
        return SeriesLoaderAdapter {
            adapter.retry()
        }
    }

    override fun onTextSubmit(query: String?) {
        lifecycleScope.launch {
            val queryText = query ?: return@launch
            viewModel.searchSeries(queryText).distinctUntilChanged().apply {
                collectLatest {
                    (adapter as? SeriesPaginationAdapter)?.submitData(it)
                }
            }
        }

    }

    override fun removeHistory(history: History) {
        viewModel.removeHistory(history)
    }

    override fun configStateView(stateView: StateView) {
        stateView.setTitle(R.string.tv_show_search_empty_title)
        stateView.setStateIcon(com.vlv.imperiya.core.R.drawable.ic_series)
    }

    override fun configWarningView(smallWarningView: SmallWarningView) {

    }

    override fun addHistory(text: String?) {
        text?.let {
            viewModel.addToHistory(text)
        }
    }
}