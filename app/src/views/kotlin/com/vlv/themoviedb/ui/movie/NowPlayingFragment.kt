package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.vlv.extensions.*
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : MovieCarouselFragment() {

    private val viewModel: NowPlayingViewModel by viewModel()
    override val carouselDecorator: CarouselDecorator
        get() = CarouselDecorator(edgeTimeBaseline = 10)

    override val titleRes: Int
        get() = R.string.now_playing_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.nowPlaying().observe(viewLifecycleOwner) {
            data {
                viewStateMachine.dataState()
                (recyclerView.adapter as? MoviesCarouselAdapter)?.submitList(it.movies)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
        }
        setupStateView()
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