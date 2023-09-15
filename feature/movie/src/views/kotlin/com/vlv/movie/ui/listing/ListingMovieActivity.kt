package com.vlv.movie.ui.listing

import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.common.ui.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.movie.R
import com.vlv.common.R as RCommon
import com.vlv.movie.data.toDetailObject
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import com.vlv.movie.ui.adapter.VIEW_TYPE_MOVIE
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
        warningView.setTitle(R.string.movie_listing_error_title)
        warningView.setBody(R.string.movie_listing_error_body)
        warningView.setButtonText(R.string.movie_listing_error_button)
        warningView.setVisibilityIcon(show = false)
        warningView.setOnTryAgain {
            pagingAdapter.retry()
        }
    }

    override fun loaderAdapter() = MovieLoaderAdapter {
        pagingAdapter.retry()
    }

}