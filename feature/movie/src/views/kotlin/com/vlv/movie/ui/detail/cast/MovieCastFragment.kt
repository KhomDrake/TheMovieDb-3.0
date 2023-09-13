package com.vlv.movie.ui.detail.cast

import androidx.core.os.bundleOf
import br.com.arch.toolkit.delegate.extraProvider
import com.vlv.common.ui.cast.CastFragment
import com.vlv.common.ui.cast.adapter.CastAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.movie.data.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_MOVIE = "EXTRA_MOVIE"

class MovieCastFragment : CastFragment() {

    private val viewModel: CastViewModel by viewModel()

    private val movie: Movie? by extraProvider(EXTRA_MOVIE)


    override fun loadCast() {
        val movie = movie ?: return
        viewModel.movieCast(movie.id).observe(viewLifecycleOwner) {
            data { castList ->
                viewStateMachine.dataState()
                (cast.adapter as? CastAdapter)?.submitList(castList)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

    companion object {

        fun instance(movie: Movie) = MovieCastFragment().apply {
            arguments = bundleOf(
                EXTRA_MOVIE to movie
            )
        }

    }

}