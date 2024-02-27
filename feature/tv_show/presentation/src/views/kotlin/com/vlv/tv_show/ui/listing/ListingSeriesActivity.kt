package com.vlv.tv_show.ui.listing

import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.tv_show.TvShowListType
import com.vlv.common.data.tv_show.toDetailObject
import com.vlv.common.route.TV_SHOW_LISTING_TYPE_EXTRA
import com.vlv.common.route.toTvShowsDetail
import com.vlv.common.ui.adapter.series.SeriesLoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesPaginationAdapter
import com.vlv.common.ui.adapter.series.VIEW_TYPE_SERIES
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.tv_show.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListingSeriesActivity : ListingItemsActivity() {

    private val type: TvShowListType by extraProvider(
        TV_SHOW_LISTING_TYPE_EXTRA, default = TvShowListType.TRENDING
    )

    private val viewModel: ListingSeriesViewModel by viewModel()

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

    override val title: Int
        get() = when(type) {
            TvShowListType.POPULAR -> R.string.tv_show_popular_title
            TvShowListType.TRENDING -> R.string.tv_show_trending_title
            TvShowListType.AIRING_TODAY -> R.string.tv_show_airing_today_title
            TvShowListType.TOP_RATED -> R.string.tv_show_top_rated_title
            TvShowListType.ON_THE_AIR -> R.string.tv_show_on_the_air_title
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

    override fun createLayoutManager() = GridLayoutManager(this, 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = pagingAdapter.getItemViewType(position)

                return if (viewType == VIEW_TYPE_SERIES) 1 else 2
            }

        }
    }

    override fun configWarningView() {
        warningView.setCloseIcon {
            finish()
        }
        warningView.setOnTryAgain {
            pagingAdapter.retry()
        }
    }

    override fun loaderAdapter() = SeriesLoaderAdapter {
        pagingAdapter.retry()
    }

}