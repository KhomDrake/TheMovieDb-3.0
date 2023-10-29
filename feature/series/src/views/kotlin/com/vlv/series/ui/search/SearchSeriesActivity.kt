package com.vlv.series.ui.search

import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.data.series.toDetailObject
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesLoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesPaginationAdapter
import com.vlv.common.ui.route.toSeriesDetail
import com.vlv.common.ui.search.SearchActivity
import com.vlv.imperiya.ui.stateview.StateView
import com.vlv.imperiya.ui.warning.SmallWarningView
import com.vlv.network.database.data.History
import com.vlv.series.R
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
            toSeriesDetail(series.toDetailObject()),
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                getString(com.vlv.common.R.string.common_poster_transition_name)
            ).toBundle()
        )
    }

    override val loadingLayout: Int
        get() = com.vlv.common.R.layout.common_listing_series_loading

    override val searchHint: Int
        get() = R.string.series_search_hint

    override val titleHistoryTitle: Int
        get() = R.string.series_search_history_title

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
        stateView.setTitle(R.string.series_search_empty_title)
        stateView.setStateIcon(com.vlv.imperiya.R.drawable.ic_series)
    }

    override fun configWarningView(smallWarningView: SmallWarningView) {

    }

    override fun addHistory(text: String?) {
        text?.let {
            viewModel.addToHistory(text)
        }
    }
}