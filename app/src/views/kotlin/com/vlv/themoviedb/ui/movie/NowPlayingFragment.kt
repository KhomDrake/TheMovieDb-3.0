package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingFragment : MovieCarouselFragment() {

    private val viewModel: NowPlayingViewModel by viewModel()
    override val carouselDecorator: CarouselDecorator
        get() = CarouselDecorator(edgeTimeBaseline = 10)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = "Now Playing"
        viewModel.nowPlaying().observe(viewLifecycleOwner) {
            data {
                (recyclerView.adapter as? MoviesCarouselAdapter)?.submitList(it.movies)
            }
        }
    }

}