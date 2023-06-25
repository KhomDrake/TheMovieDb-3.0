package com.vlv.themoviedb.ui.movie.nowplaying

import android.os.Bundle
import android.view.View
import com.vlv.extensions.*
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.MovieCarouselFragment
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
                if(it.movies.isEmpty()) viewStateMachine.emptyState()
                else viewStateMachine.dataState()
                (recyclerView.adapter as? MoviesCarouselAdapter)?.submitList(it.movies)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
        }
    }

}