package com.vlv.themoviedb.ui.movie.trending

import com.vlv.common.ui.route.toMovieTrending
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.MovieCarouselFragment
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingNowFragment : MovieCarouselFragment() {

    private val viewModel: TrendingNowViewModel by viewModel()

    override val carouselDecorator: CarouselDecorator
        get() = CarouselDecorator(edgeTimeBaseline = 4)

    override val titleRes: Int
        get() = R.string.trending_title

    override fun configEmptyView() {
        emptyView.apply {
            setTitle(R.string.empty_state_text_movie_trending)
            setStateIcon(com.vlv.imperiya.R.drawable.ic_movie)
        }
    }

    override fun configErrorView() {
        errorView.apply {
            setTitleText(R.string.error_movie_load_text_title_trending)
            setBodyText(R.string.error_load_text_body)
            setButtonText(R.string.error_load_text_button)
            setOnClickLink {
                loadContent()
            }
        }
    }

    override fun loadContent() {
        viewModel.trendingMovies().observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState()
                else viewStateMachine.dataState()
                (recyclerView.adapter as? MoviesCarouselAdapter)?.submitList(it)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

    override fun onClickSeeAll() {
        startActivity(
            requireContext().toMovieTrending()
        )
    }

}