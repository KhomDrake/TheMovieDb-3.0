package com.vlv.themoviedb.ui.movie.favorites

import android.os.Bundle
import android.view.View
import com.vlv.extensions.emptyState
import com.vlv.extensions.loadingState
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.MovieCarouselFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoritesFragment : MovieCarouselFragment() {

    override val titleRes: Int
        get() = R.string.favorites_title

    override val emptyTextRes: Int
        get() = R.string.empty_state_text_movie_favorite

    private val viewModel: MovieFavoritesViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favorites().observe(viewLifecycleOwner) {
            data {
                viewStateMachine.emptyState()
            }
            showLoading {
                viewStateMachine.loadingState()
            }
        }
    }

}