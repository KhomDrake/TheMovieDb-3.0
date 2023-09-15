package com.vlv.themoviedb.ui.movie.nowplaying

import android.os.Bundle
import android.view.View
import com.vlv.common.ui.route.toMovieNowPlaying
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
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

    override fun configEmptyView() {
        emptyView.apply {
            setTitle(R.string.empty_state_text_movie_now_playing)
            setStateIcon(com.vlv.imperiya.R.drawable.ic_movie)
        }
    }

    override fun configErrorView() {
        errorView.apply {
            setTitleText(R.string.error_movie_load_text_title_now_playing)
            setBodyText(R.string.error_movie_load_text_body)
            setButtonText(R.string.error_movie_load_text_button)
            setOnClickLink {
                loadContent()
            }
        }
    }

    override fun loadContent() {
        viewModel.nowPlaying().observe(viewLifecycleOwner) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadContent()
    }

    override fun onClickSeeAll() {
        startActivity(
            requireContext().toMovieNowPlaying()
        )
    }

}