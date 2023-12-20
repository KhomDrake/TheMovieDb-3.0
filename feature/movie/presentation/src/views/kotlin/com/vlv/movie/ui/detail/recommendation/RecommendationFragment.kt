package com.vlv.movie.ui.detail.recommendation

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.movie.MoviePaginationAdapter
import com.vlv.common.ui.adapter.movie.VIEW_TYPE_MOVIE
import com.vlv.common.ui.listing.ListingItemsFragment
import com.vlv.common.route.toMovieDetail
import com.vlv.extensions.errorState
import com.vlv.movie.R
import com.vlv.movie.ui.detail.cast.EXTRA_MOVIE
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendationFragment : ListingItemsFragment() {

    private val viewModel: RecommendationViewModel by viewModel()

    private val movie: Movie? by extraProvider(EXTRA_MOVIE)

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_listing_movie_loading

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    private val pagingAdapter = MoviePaginationAdapter { movie, view ->
        val intent = requireContext().toMovieDetail(movie.toDetailObject())
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view,
                getString(com.vlv.ui.R.string.common_poster_transition_name)
            ).toBundle()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = movie ?: return run {
            viewStateMachine.errorState()
        }
        lifecycleScope.launch {
            viewModel.recommendations(movie.id).distinctUntilChanged().apply {
                collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun configEmptyState() {
        emptyState.apply {
            setStateIcon(com.vlv.imperiya.core.R.drawable.ic_movie)
            setTitle(R.string.movie_empty_state_recommendation)
        }
    }

    override fun configWarningView() {
        warningView.setOnClickLink {
            pagingAdapter.retry()
        }
    }

    override fun loaderAdapter() = MovieLoaderAdapter {
        pagingAdapter.retry()
    }

    override fun createLayoutManager() = GridLayoutManager(requireContext(), 2).apply {
        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = pagingAdapter.getItemViewType(position)

                return if (viewType == VIEW_TYPE_MOVIE) 1 else 2
            }

        }
    }

    companion object {

        fun instance(movie: Movie) = RecommendationFragment().apply {
            arguments = bundleOf(
                EXTRA_MOVIE to movie
            )
        }

    }

}