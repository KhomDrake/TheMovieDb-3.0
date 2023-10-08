package com.vlv.search.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.data.series.toDetailObject
import com.vlv.common.ui.adapter.movie.MovieLoaderAdapter
import com.vlv.common.ui.adapter.movie.MoviePaginationAdapter
import com.vlv.common.ui.adapter.people.PeopleLoaderAdapter
import com.vlv.common.ui.adapter.people.PeoplePagingAdapter
import com.vlv.common.ui.adapter.searchhistory.HistoryAdapter
import com.vlv.common.ui.adapter.series.SeriesLoaderAdapter
import com.vlv.common.ui.adapter.series.SeriesPaginationAdapter
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.common.ui.route.toPeopleDetail
import com.vlv.common.ui.route.toSeriesDetail
import com.vlv.extensions.State
import com.vlv.extensions.dataState
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.initialState
import com.vlv.extensions.isInInitialState
import com.vlv.extensions.loadingState
import com.vlv.extensions.setupState
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateInitial
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.search.ImperiyaSearchView
import com.vlv.imperiya.ui.warning.SmallWarningView
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
    private val warning: SmallWarningView by viewProvider(R.id.warning_view)
    private val searchView: ImperiyaSearchView by viewProvider(R.id.search)
    private val items: RecyclerView by viewProvider(R.id.items)
    private val historyItems: RecyclerView by viewProvider(R.id.history_items)
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
        setupHistoryAdapter()

        filter.setOnCheckedStateChangeListener { _, checkedIds ->
            options.firstOrNull { it.id == checkedIds.firstOrNull() }?.let { chip ->
                updateLastChecked(chip)
            }
        }

        viewModel.searchType.observe(viewLifecycleOwner) { searchType ->
            searchView.clearText()
            setupSearchBySearchType(searchType)
            loadHistory()
        }


        searchView.apply {
            setup(onTextSubmit = ::onTextSubmit)
            onClickCloseListener {
                clearText()
                viewStateMachine.initialState()
                loadHistory()
            }
        }

        warning.setOnClickLink {
            adapter?.retry()
        }
    }

    private fun loadHistory() {
        viewModel.historyBySearchType().observe(viewLifecycleOwner) {
            (historyItems.adapter as? HistoryAdapter)?.submitList(it)
            historyItems.isVisible = it.isEmpty().not() && viewStateMachine.isInInitialState()
        }
    }

    private fun setupHistoryAdapter() {
        historyItems.layoutManager = LinearLayoutManager(requireContext())
        historyItems.adapter = HistoryAdapter(
            onClick = { history ->
                searchView.setText(history.text)
                onTextSubmit(history.text)
            },
            onClickClose = { history ->
                viewModel.removeHistory(history)
            }
        )
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            defaultConfig(root, initial = State.INITIAL)

            stateInitial {
                visibles(historyItems)
                gones(items, loading, warning)
            }

            stateData {
                visibles(items)
                gones(loading, warning, historyItems)
            }

            stateError {
                visibles(warning)
                gones(loading, items, historyItems)
            }

            stateEmpty {
                visibles(items)
                gones(loading, warning, historyItems)
            }

            stateLoading {
                visibles(loading)
                gones(items, warning, historyItems)
            }
        }
    }

    private fun onTextSubmit(query: String?) {
        lifecycleScope.launch {
            val queryText = query.toString()
            val searchType = viewModel.searchType.value ?: SearchType.MOVIE
            viewModel.addToHistory(queryText)
            when(searchType) {
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
        viewStateMachine.initialState()
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
                val pagingAdapter = SeriesPaginationAdapter { series, view ->
                    startActivity(
                        requireContext().toSeriesDetail(series.toDetailObject()),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            view,
                            getString(com.vlv.common.R.string.common_poster_transition_name)
                        ).toBundle()
                    )
                }
                adapter = pagingAdapter
                items.adapter = pagingAdapter.withLoadStateFooter(SeriesLoaderAdapter {
                    pagingAdapter.retry()
                })

            }
            SearchType.PERSON -> {
                items.layoutManager = GridLayoutManager(requireContext(), 3)
                val pagingAdapter = PeoplePagingAdapter { image, people ->
                    startActivity(
                        requireContext().toPeopleDetail(people),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            image,
                            getString(com.vlv.common.R.string.common_avatar_transition_name)
                        ).toBundle()
                    )
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

    override fun onDestroyView() {
        items.adapter = null
        super.onDestroyView()
    }

}