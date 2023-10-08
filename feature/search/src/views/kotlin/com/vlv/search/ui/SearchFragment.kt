package com.vlv.search.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.movie.MoviePaginationAdapter
import com.vlv.common.ui.adapter.people.PeopleLoaderAdapter
import com.vlv.common.ui.adapter.people.PeoplePagingAdapter
import com.vlv.common.ui.adapter.series.SeriesLoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesPaginationAdapter
import com.vlv.common.ui.route.toMovieDetail
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
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.imperiya.ui.warning.WarningView
import com.vlv.search.R
import com.vlv.search.data.SearchType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModel()

    private val viewStateMachine = ViewStateMachine()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val loading: ShimmerFrameLayout by viewProvider(R.id.loading)
    private val warning: WarningView by viewProvider(R.id.warning_view)
    private val searchView: ImperiyaSearchView by viewProvider(R.id.search)
    private val items: RecyclerView by viewProvider(R.id.items)
    private val filter: ChipGroup by viewProvider(R.id.filter)
    private val seriesOption: Chip by viewProvider(R.id.series_option)
    private val movieOption: Chip by viewProvider(R.id.movie_option)
    private val personOption: Chip by viewProvider(R.id.person_option)
    private var adapter: PagingDataAdapter<*, *>? = null

    private var lastChecked: Chip? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStateMachine()
        val options = listOf(movieOption, seriesOption, personOption)
        updateLastChecked(movieOption)
        filter.setOnCheckedStateChangeListener { _, checkedIds ->
            options.firstOrNull { it.id == checkedIds.firstOrNull() }?.let { chip ->
                updateLastChecked(chip)
            }
        }

        viewModel.searchType.observe(viewLifecycleOwner) { searchType ->
            setupSearchBySearchType(searchType)
        }

        searchView.setup(
            onTextSubmit = ::onTextSubmit
        )

        warning.setOnTryAgain {
            adapter?.retry()
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root)

            stateData {
                visibles(items)
                gones(loading, warning)
            }

            stateError {
                visibles(warning)
                gones(loading, items)
            }

            stateEmpty {
                visibles(items)
                gones(loading, warning)
            }

            stateLoading {
                visibles(loading)
                gones(items, warning)
            }
        }
    }

    private fun onTextSubmit(query: String?) {
        lifecycleScope.launch {
            val queryText = query.toString()
            when(viewModel.searchType.value ?: SearchType.MOVIE) {
                SearchType.MOVIE -> {
                    loadSearchMovie(queryText)
                }
                SearchType.SERIES -> {
                    loadSearchSeries(queryText)
                }
                SearchType.PERSON -> {
                    loadSearchPerson(queryText)
                }
            }
        }
    }

    private suspend fun loadSearchMovie(query: String) {
        viewModel.searchMovie(query).distinctUntilChanged().apply {
            collectLatest {
                (adapter as? MoviePaginationAdapter)?.submitData(it)
            }
        }
    }

    private suspend fun loadSearchSeries(query: String) {
        viewModel.searchSeries(query).distinctUntilChanged().apply {
            collectLatest {
                (adapter as? SeriesPaginationAdapter)?.submitData(it)
            }
        }
    }

    private suspend fun loadSearchPerson(query: String) {
        viewModel.searchPeople(query).distinctUntilChanged().apply {
            collectLatest {
                (adapter as? PeoplePagingAdapter)?.submitData(it)
            }
        }
    }

    private fun setupSearchBySearchType(searchType: SearchType) {
        viewStateMachine.dataState()
        adapter = null
        items.adapter = null
        updateLayoutLoading(searchType)
        when(searchType) {
            SearchType.MOVIE -> {
                items.layoutManager = GridLayoutManager(requireContext(), 2)
                val pagingAdapter = MoviePaginationAdapter { movie, view ->
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
                adapter = pagingAdapter

                items.adapter = pagingAdapter.withLoadStateFooter(MovieLoaderAdapter {
                    pagingAdapter.retry()
                })
            }
            SearchType.SERIES -> {
                items.layoutManager = GridLayoutManager(requireContext(), 2)
                val pagingAdapter = SeriesPaginationAdapter { _, _ ->

                }
                adapter = pagingAdapter
                items.adapter = pagingAdapter.withLoadStateFooter(SeriesLoaderAdapter {
                    pagingAdapter.retry()
                })

            }
            SearchType.PERSON -> {
                items.layoutManager = GridLayoutManager(requireContext(), 3)
                val pagingAdapter = PeoplePagingAdapter { _, _ ->

                }
                adapter = pagingAdapter
                items.adapter = pagingAdapter.withLoadStateFooter(PeopleLoaderAdapter {
                    pagingAdapter.retry()
                })
            }
        }
        configAdapter()
    }

    private fun updateLayoutLoading(searchType: SearchType) {
        loading.removeAllViews()
        View.inflate(requireContext(), searchType.layout, loading)
    }

    private fun configAdapter() {
        adapter?.setupState(
            onData = {
                viewStateMachine.dataState()
            },
            onEmpty = {
                viewStateMachine.emptyState()
            },
            onError = { _  ->
                viewStateMachine.errorState()
            },
            onLoading = {
                viewStateMachine.loadingState()
            }
        )
    }

    private fun updateLastChecked(chip: Chip) {
        lastChecked?.isClickable = true
        lastChecked = chip
        chip.isClickable = false
        val type = SearchType.values().find {
            it.name.lowercase() == chip.tag.toString()
        } ?: SearchType.MOVIE
        viewModel.setSearchType(type)
    }

}