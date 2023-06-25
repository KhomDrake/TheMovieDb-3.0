package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import br.com.arch.toolkit.statemachine.setup
import com.vlv.extensions.*
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.R
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
        setupStateView()
        viewModel.trendingMovies().observe(viewLifecycleOwner) {
            data {
                viewStateMachine.dataState()
                (recyclerView.adapter as? MoviesCarouselAdapter)?.submitList(it.movies)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->

            }
        }
    }

    private fun setupStateView() {
        viewStateMachine.setup {
            stateData {
                visibles(recyclerView)
                gones(shimmer)
            }
            stateLoading {
                visibles(shimmer)
                gones(recyclerView)
                onEnter {
                    shimmer.startShimmer()
                }
            }
        }
    }

}