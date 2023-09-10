package com.vlv.movie.ui.listing

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.common.ui.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListingMovieActivity : ListingItemsActivity() {

    private val viewModel: ListingMovieViewModel by viewModel()

    private val type: MovieListType by
        extraProvider(MOVIES_LISTING_TYPE_EXTRA, MovieListType.TRENDING)

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    override val loadingLayout: Int
        get() = R.layout.movie_listing_movie_loading

    private val pagingAdapter = MoviePaginationAdapter()

    override val title: Int
        get() = when(type) {
            MovieListType.TRENDING -> R.string.movie_title_trending_now
            MovieListType.POPULAR -> R.string.movie_title_popular
            MovieListType.TOP_RATED -> R.string.movie_title_top_rated
            MovieListType.UPCOMING -> R.string.movie_title_upcoming
            MovieListType.NOW_PLAYING -> R.string.movie_title_now_playing
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.loadMoviesByType(type).distinctUntilChanged().apply {
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

    override fun loaderAdapter() = MovieLoaderAdapter {
        pagingAdapter.retry()
    }

}