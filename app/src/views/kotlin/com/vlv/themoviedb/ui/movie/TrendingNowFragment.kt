package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingNowFragment : MovieCarouselFragment() {

    private val viewModel: TrendingNowViewModel by viewModel()

    override val carouselDecorator: CarouselDecorator
        get() = CarouselDecorator(edgeTimeBaseline = 4)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = "Trending Now"
        viewModel.trendingMovies().observe(viewLifecycleOwner) {
            data {
                (recyclerView.adapter as? MoviesCarouselAdapter)?.submitList(it.movies)
            }
        }
    }

}