package com.vlv.tv_show.ui.detail.review

import androidx.core.os.bundleOf
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.ui.review.ReviewFragment
import com.vlv.common.ui.review.adapter.ReviewAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.tv_show.ui.detail.about.EXTRA_TV_SHOW
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesReviewFragment : ReviewFragment() {

    private val series: TvShow? by extraProvider(EXTRA_TV_SHOW, null)

    private val viewModel: SeriesReviewViewModel by viewModel()

    override fun loadReviews() {
        val series = series ?: return
        viewModel.reviews(series.id).observe(viewLifecycleOwner) {
            data { reviewsList ->
                if(reviewsList.isEmpty()) {
                    viewStateMachine.emptyState()
                } else {
                    viewStateMachine.dataState()
                }
                (reviews.adapter as? ReviewAdapter)?.submitList(reviewsList)
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
        fun instance(series: TvShow) = SeriesReviewFragment().apply {
            arguments = bundleOf(
                EXTRA_TV_SHOW to series
            )
        }
    }

}