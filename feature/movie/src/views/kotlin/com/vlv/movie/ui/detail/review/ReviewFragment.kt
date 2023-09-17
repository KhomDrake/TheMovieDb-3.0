package com.vlv.movie.ui.detail.review

import androidx.core.os.bundleOf
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.movie.Movie
import com.vlv.common.ui.review.ReviewFragment
import com.vlv.common.ui.review.adapter.ReviewAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.movie.ui.detail.cast.EXTRA_MOVIE
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieReviewFragment : ReviewFragment() {

    private val viewModel: ReviewViewModel by viewModel()

    private val movie: Movie? by extraProvider(EXTRA_MOVIE)

    override fun loadReviews() {
        val movie = movie ?: return

        viewModel.movieReviews(movie.id).observe(viewLifecycleOwner) {
            data { reviewList ->
                if(reviewList.isEmpty()) {
                    viewStateMachine.emptyState()
                } else {
                    viewStateMachine.dataState()
                }
                (reviews.adapter as? ReviewAdapter)?.submitList(reviewList)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

    companion object {

        fun instance(movie: Movie) = MovieReviewFragment().apply {
            arguments = bundleOf(
                EXTRA_MOVIE to movie
            )
        }

    }

}