package com.vlv.themoviedb.ui.movie.trending

import android.os.Bundle
import android.view.View
import com.vlv.common.ui.route.toMovieTrendingNow
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

            }
        }
    }

    override fun onClickSeeAll() {
        startActivity(
            requireContext().toMovieTrendingNow()
        )
    }

}