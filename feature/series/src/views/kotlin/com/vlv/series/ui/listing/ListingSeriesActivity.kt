package com.vlv.series.ui.listing

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.series.SeriesListType
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.common.ui.route.SERIES_LISTING_TYPE_EXTRA
import com.vlv.series.R
import com.vlv.series.ui.adapter.SeriesPaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListingSeriesActivity : ListingItemsActivity() {

    private val type: SeriesListType by extraProvider(
        SERIES_LISTING_TYPE_EXTRA, default = SeriesListType.TRENDING
    )

    private val viewModel: ListingSeriesViewModel by viewModel()

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    private val pagingAdapter = SeriesPaginationAdapter()

    override val loadingLayout: Int
        get() = R.layout.series_listing_series_loading

    override val title: Int
        get() = when(type) {
            SeriesListType.POPULAR -> R.string.series_popular_title
            SeriesListType.TRENDING -> R.string.series_trending_title
            SeriesListType.AIRING_TODAY -> R.string.series_airing_today_title
            SeriesListType.TOP_RATED -> R.string.series_top_rated_title
            SeriesListType.ON_THE_AIR -> R.string.series_on_the_air_title
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadingType(type).distinctUntilChanged().apply {
                collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun configWarningView() {
        warningView.setTitle("WarningTitle")
        warningView.setBody("WarningBody")
        warningView.setButtonText("WarningButton")
        warningView.setOnTryAgain {
            pagingAdapter.retry()
        }
    }

    override fun loaderAdapter() = SeriesLoaderAdapter {
        pagingAdapter.retry()
    }

}