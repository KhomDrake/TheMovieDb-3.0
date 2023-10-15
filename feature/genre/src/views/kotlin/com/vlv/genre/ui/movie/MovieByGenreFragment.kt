package com.vlv.genre.ui.movie

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.movie.MoviePaginationAdapter
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.setupState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.genre.R
import com.vlv.imperiya.ui.warning.SmallWarningView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

const val GENRE_ID_EXTRA = "GENRE_ID_EXTRA"

class MovieByGenreFragment : Fragment(R.layout.genre_fragment_by_genre) {

    private val viewModel: MovieGenreViewModel by viewModel()

    private val genreId: Int by extraProvider(GENRE_ID_EXTRA, 0)

    private val root: ViewGroup by viewProvider(R.id.root)
    private val loading: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val error: SmallWarningView by viewProvider(R.id.error)
    private val items: RecyclerView by viewProvider(R.id.items)

    private val viewStateMachine = ViewStateMachine()

    val adapter = MoviePaginationAdapter { movie, view ->
        val intent = requireContext().toMovieDetail(movie.toDetailObject())
        startActivity(
            intent,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                view,
                getString(com.vlv.common.R.string.common_poster_transition_name)
            ).toBundle()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        View.inflate(
            requireContext(),
            com.vlv.common.R.layout.common_listing_movie_loading,
            loading
        )
        setupViewStateMachine()
        setupRecyclerView()
        loadMovies()
        error.setOnClickLink {
            adapter.retry()
        }
    }

    private fun setupRecyclerView() {
        items.layoutManager = GridLayoutManager(requireContext(), 2)
        items.adapter = adapter.withLoadStateFooter(MovieLoaderAdapter {
            adapter.retry()
        })

        adapter.setupState(
            onData = {
                viewStateMachine.dataState()
            },
            onLoading = {
                viewStateMachine.loadingState()
            },
            onError = {
                viewStateMachine.errorState()
            },
            onEmpty = {
                viewStateMachine.dataState()
            }
        )
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            viewModel.moviesByGenre(genreId).distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items)
                gones(loading, error)
            }

            stateError {
                visibles(error)
                gones(loading, items)
            }

            stateLoading {
                visibles(loading)
                gones(items, error)
            }
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        items.adapter = null
        super.onDestroyView()
    }

    companion object {
        fun instance(genreId: Int) = MovieByGenreFragment().apply {
            arguments = bundleOf(
                GENRE_ID_EXTRA to genreId
            )
        }
    }

}