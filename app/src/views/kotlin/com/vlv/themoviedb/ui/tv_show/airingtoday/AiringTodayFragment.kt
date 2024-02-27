package com.vlv.themoviedb.ui.tv_show.airingtoday

import android.os.Bundle
import android.view.View
import com.vlv.common.route.toTvShowAiringToday
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.tv_show.SeriesCarouselFragment
import com.vlv.themoviedb.ui.tv_show.adapter.SeriesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AiringTodayFragment : SeriesCarouselFragment() {

    private val viewModel: AiringTodayViewModel by viewModel()

    override val titleRes: Int
        get() = R.string.airing_today_title

    override fun configEmptyView() {
        emptyView.apply {
            setTitle(R.string.empty_state_text_series_airing_today)
            setStateIcon(com.vlv.imperiya.core.R.drawable.ic_tv_off)
        }
    }

    override fun configErrorView() {
        errorView.apply {
            setTitleText(R.string.error_series_load_text_title_airing_today)
            setBodyText(R.string.error_load_text_body)
            setButtonText(R.string.error_load_text_button)
            setOnClickLink {
                loadContent()
            }
        }
    }

    override fun loadContent() {
        viewModel.airingToday().observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState()
                else viewStateMachine.dataState()
                (recyclerView.adapter as? SeriesCarouselAdapter)?.submitList(it)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadContent()
    }

    override fun onClickSeeAll() {
        startActivity(requireContext().toTvShowAiringToday())
    }

}