package com.vlv.genre.ui.series

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
import com.vlv.common.data.series.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesPaginationAdapter
import com.vlv.common.ui.route.toSeriesDetail
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.extensions.setupState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.genre.R
import com.vlv.genre.ui.movie.GENRE_ID_EXTRA
import com.vlv.imperiya.ui.stateview.StateView
import com.vlv.imperiya.ui.warning.SmallWarningView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesByGenreFragment : Fragment(R.layout.genre_fragment_by_genre) {

    private val viewModel: SeriesGenreViewModel by viewModel()

    private val genreId: Int? by extraProvider(GENRE_ID_EXTRA, null)

    private val root: ViewGroup by viewProvider(R.id.root)
    private val loading: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val error: SmallWarningView by viewProvider(R.id.error)
    private val emptyState: StateView by viewProvider(R.id.empty_state)
    private val items: RecyclerView by viewProvider(R.id.items)

    private val viewStateMachine = ViewStateMachine()

    val adapter = SeriesPaginationAdapter { series, view ->
        val intent = requireContext().toSeriesDetail(series.toDetailObject())
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
            com.vlv.common.R.layout.common_listing_series_loading,
            loading
        )
        setupViewStateMachine()
        setupRecyclerView()
        loadMovies()
        emptyState.apply {
            setTitle(R.string.genre_no_series_found)
            setStateIcon(com.vlv.imperiya.R.drawable.ic_tv)
        }
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
                viewStateMachine.emptyState()
            }
        )
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            val genreId = genreId ?: return@launch run {
                viewStateMachine.errorState()
            }
            viewModel.seriesByGenre(genreId).distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items)
                gones(loading, error, emptyState)
            }

            stateError {
                visibles(error)
                gones(loading, items, emptyState)
            }

            stateLoading {
                visibles(loading)
                gones(items, error, emptyState)
            }

            stateEmpty {
                visibles(emptyState)
                gones(items, error, loading)
            }
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        items.adapter = null
        super.onDestroyView()
    }

    companion object {
        fun instance(genreId: Int) = SeriesByGenreFragment().apply {
            arguments = bundleOf(
                GENRE_ID_EXTRA to genreId
            )
        }
    }

}