package com.vlv.series.ui.detail.recommendation

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.R
import com.vlv.common.ui.listing.ListingItemsFragment
import com.vlv.common.ui.route.toSeriesDetail
import com.vlv.series.data.Series
import com.vlv.series.data.toDetailObject
import com.vlv.series.ui.detail.about.EXTRA_SERIES
import com.vlv.series.ui.listing.SeriesLoaderAdapter
import com.vlv.series.ui.listing.SeriesPaginationAdapter
import com.vlv.series.ui.listing.VIEW_TYPE_SERIES
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendationFragment : ListingItemsFragment() {

    private val series: Series? by extraProvider(EXTRA_SERIES, null)

    private val viewModel: RecommendationViewModel by viewModel()

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    private val pagingAdapter = SeriesPaginationAdapter { series, view ->
        startActivity(
            requireContext().toSeriesDetail(series.toDetailObject()),
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view,
                getString(R.string.common_poster_transition_name)
            ).toBundle()
        )
    }

    override val loadingLayout: Int
        get() = com.vlv.series.R.layout.series_listing_series_loading

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val series = series ?: return
        lifecycleScope.launch {
            viewModel.recommendations(series.id).distinctUntilChanged().apply {
                collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun createLayoutManager() = GridLayoutManager(requireContext(), 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = pagingAdapter.getItemViewType(position)

                return if (viewType == VIEW_TYPE_SERIES) 1 else 2
            }

        }
    }

    override fun loaderAdapter() = SeriesLoaderAdapter {
        pagingAdapter.retry()
    }

    override fun configEmptyState() {
        emptyState.apply {
            setStateIcon(com.vlv.imperiya.R.drawable.ic_movie)
            setTitle(com.vlv.series.R.string.series_empty_state_recommendation)
        }
    }

    override fun configWarningView() {
        warningView.setOnClickLink {
            pagingAdapter.retry()
        }
    }

    companion object {
        fun instance(series: Series) = RecommendationFragment().apply {
            arguments = bundleOf(
                EXTRA_SERIES to series
            )
        }
    }

}