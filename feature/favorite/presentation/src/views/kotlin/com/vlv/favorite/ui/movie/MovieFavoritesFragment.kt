package com.vlv.favorite.ui.movie

import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieAdapter
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.favorite.R
import com.vlv.favorite.ui.BaseFavoriteFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoritesFragment : BaseFavoriteFragment() {

    private val viewModel: MovieFavoritesViewModel by viewModel()

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_listing_movie_loading

    override fun setupStateView() {
        emptyState.setStateIcon(com.vlv.imperiya.core.R.drawable.ic_movie)
        emptyState.setTitle(R.string.favorite_movie_empty_state)
    }

    override fun setupRecyclerView() {
        items.layoutManager = GridLayoutManager(requireContext(), 2)
        items.adapter = MovieAdapter { movie, view ->
            val intent = requireContext().toMovieDetail(
                movie.toDetailObject(),
                finishAfterTransition = false
            )
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    view,
                    getString(com.vlv.ui.R.string.common_poster_transition_name)
                ).toBundle()
            )
        }
    }

    override fun loadFavorites() {
        viewModel.movieFavorites().observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState() else viewStateMachine.dataState()

                (items.adapter as? MovieAdapter)?.submitList(it)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { e ->
                viewStateMachine.errorState()
            }
        }
    }


}