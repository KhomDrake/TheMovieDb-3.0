package com.vlv.movie.presentation.ui.listing

import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.movie.MoviePaginationAdapter
import com.vlv.common.ui.adapter.movie.VIEW_TYPE_MOVIE
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.common.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.common.route.toMovieDetail
import com.vlv.movie.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.vlv.ui.R as RCommon

class ListingMovieActivity : ListingItemsActivity() {

    private val viewModel: ListingMovieViewModel by viewModel()

    private val type: MovieListType by
        extraProvider(MOVIES_LISTING_TYPE_EXTRA, MovieListType.TRENDING)

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_listing_movie_loading

    private val pagingAdapter = MoviePaginationAdapter { movie, view ->
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

    override fun createLayoutManager() = GridLayoutManager(this, 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = pagingAdapter.getItemViewType(position)

                return if (viewType == VIEW_TYPE_MOVIE) 1 else 2
            }

        }
    }

    override fun configWarningView() {
        warningView.setOnTryAgain {
            pagingAdapter.retry()
        }
        warningView.setCloseIcon {
            finish()
        }
    }

    override fun loaderAdapter() = MovieLoaderAdapter {
        pagingAdapter.retry()
    }

}