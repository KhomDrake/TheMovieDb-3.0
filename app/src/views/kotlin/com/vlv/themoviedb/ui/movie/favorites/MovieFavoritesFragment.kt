package com.vlv.themoviedb.ui.movie.favorites

import com.vlv.common.ui.route.toFavorites
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.favorite.ui.movie.MovieFavoritesViewModel
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.network.database.data.FavoriteType
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.MovieCarouselFragment
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoritesFragment : MovieCarouselFragment() {

    override val carouselDecorator: CarouselDecorator
        get() = CarouselDecorator(edgeTimeBaseline = 10)

    override val titleRes: Int
        get() = R.string.favorites_title

    private val viewModel: MovieFavoritesViewModel by viewModel()

    override fun configEmptyView() {
        emptyView.apply {
            setTitle(R.string.empty_state_text_movie_favorite)
            setStateIcon(com.vlv.imperiya.R.drawable.ic_hearts)
        }
    }

    override fun configErrorView() {
        errorView.apply {
            setTitleText(R.string.error_movie_load_text_title_favorites)
            setBodyText(R.string.error_load_text_body)
            setButtonText(R.string.error_load_text_button)
            setOnClickLink {
                loadContent()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadContent()
    }

    override fun loadContent() {
        viewModel.movieFavorites(shouldTake = true).observe(viewLifecycleOwner) {
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
        startActivity(requireContext().toFavorites(FavoriteType.MOVIE))
    }

}