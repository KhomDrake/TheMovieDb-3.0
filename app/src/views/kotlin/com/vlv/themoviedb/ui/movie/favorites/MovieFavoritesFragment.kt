package com.vlv.themoviedb.ui.movie.favorites

import android.os.Bundle
import android.view.View
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.MovieCarouselFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoritesFragment : MovieCarouselFragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favorites().observe(viewLifecycleOwner) {
            data {
                viewStateMachine.emptyState()
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

}