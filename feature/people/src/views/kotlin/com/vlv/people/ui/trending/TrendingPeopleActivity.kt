package com.vlv.people.ui.trending

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.people.R
import com.vlv.people.ui.adapter.PeopleLoaderAdapter
import com.vlv.people.ui.adapter.PeoplePagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingPeopleActivity : ListingItemsActivity() {

    private val viewModel: TrendingViewModel by viewModel()

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    private val pagingAdapter = PeoplePagingAdapter()

    override val loadingLayout: Int
        get() = R.layout.people_listing_loading

    override val title: Int
        get() = R.string.people_trending_title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.trending().distinctUntilChanged().apply {
                collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun configWarningView() {
        warningView.apply {
            setTitle(com.vlv.common.R.string.common_error_title)
            setBody(com.vlv.common.R.string.common_error_description)
            setButtonText(com.vlv.common.R.string.common_error_button)
            setCloseIcon {
                finish()
            }
            setOnTryAgain {
                pagingAdapter.retry()
            }
        }
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 3)
    }

    override fun loaderAdapter() = PeopleLoaderAdapter {
        pagingAdapter.retry()
    }

}