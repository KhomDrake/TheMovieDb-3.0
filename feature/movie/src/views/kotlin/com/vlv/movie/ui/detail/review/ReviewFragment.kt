package com.vlv.movie.ui.detail.review

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.StateMachine
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.extensions.State
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.WarningView
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import com.vlv.movie.ui.detail.cast.EXTRA_MOVIE
import com.vlv.movie.ui.detail.review.adapter.ReviewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewFragment : Fragment(R.layout.movie_fragment_review) {

    private val reviews: RecyclerView by viewProvider(R.id.review)
    private val root: ViewGroup by viewProvider(R.id.root)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_review)
    private val warningView: WarningView by viewProvider(R.id.warning_view_review)
    private val emptyView: WarningView by viewProvider(R.id.empty_view_review)
    private val viewStateMachine = ViewStateMachine()

    private val viewModel: ReviewViewModel by viewModel()

    private val movie: Movie? by extraProvider(EXTRA_MOVIE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStateMachine()
        setupRecyclerView()
        setupWarningView()
        setupEmptyState()
        loadReviews()
    }

    private fun setupEmptyState() {
        warningView.setButtonText(null)
    }

    private fun setupWarningView() {
        warningView.setOnTryAgain {
            loadReviews()
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = State.LOADING.ordinal
                onChangeState = StateMachine.OnChangeStateCallback {
                    TransitionManager.beginDelayedTransition(root)
                }
            }

            stateData {
                visibles(reviews)
                gones(warningView, shimmer, emptyView)
            }

            stateLoading {
                visibles(shimmer)
                gones(warningView, reviews, emptyView)
            }

            stateError {
                visibles(warningView)
                gones(reviews, shimmer, emptyView)
            }

            stateEmpty {
                visibles(emptyView)
                gones(reviews, shimmer, warningView)
            }
        }
    }

    private fun setupRecyclerView() {
        reviews.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        reviews.adapter = ReviewAdapter()
    }

    private fun loadReviews() {
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

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }

    companion object {

        fun instance(movie: Movie) = ReviewFragment().apply {
            arguments = bundleOf(
                EXTRA_MOVIE to movie
            )
        }

    }

}