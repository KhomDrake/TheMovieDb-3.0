package com.vlv.movie.ui.trendingnow

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingNowActivity : ListingItemsActivity() {

    private val viewModel: TrendingNowViewModel by viewModel()

    override val adapter: RecyclerView.Adapter<*>
        get() = MoviePaginationAdapter()

    override val title: Int
        get() = R.string.movie_title_trending_now

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.trendingNow().distinctUntilChanged().apply {
                collectLatest {
                    (items.adapter as? MoviePaginationAdapter)?.submitData(it)
                }
            }
        }
    }

}