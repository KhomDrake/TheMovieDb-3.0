package com.vlv.common.ui.review

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.StateMachine
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.R
import com.vlv.extensions.State
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.warning.WarningView
import com.vlv.common.ui.review.adapter.ReviewAdapter

abstract class ReviewFragment : Fragment(R.layout.common_fragment_review) {

    protected val reviews: RecyclerView by viewProvider(R.id.review)
    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_review)
    protected val warningView: WarningView by viewProvider(R.id.warning_view_review)
    protected val emptyView: WarningView by viewProvider(R.id.empty_view_review)
    protected val viewStateMachine = ViewStateMachine()

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

    abstract fun loadReviews()

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }

}