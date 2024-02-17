package com.vlv.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.SearchType
import com.vlv.common.ui.paging.MoviesPagingGrid
import com.vlv.common.ui.paging.SeriesPagingGrid
import com.vlv.data.database.data.History
import com.vlv.data.database.data.HistoryType
import com.vlv.imperiya.core.ui.components.FilterGroup
import com.vlv.imperiya.core.ui.components.FilterItemData
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.search.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    val scrollableState = rememberScrollState()
    var active by remember {
        mutableStateOf(false)
    }
    var searchingMode by remember {
        mutableStateOf(false)
    }

    val filters = listOf(
        FilterItemData(
            stringResource(id = R.string.search_movie_option), SearchType.MOVIE.name
        ),
        FilterItemData(
            stringResource(id = R.string.search_series_option), SearchType.SERIES.name
        ),
        FilterItemData(
            stringResource(id = R.string.search_people_option), SearchType.PEOPLE.name
        ),
    )

    val history by viewModel.historyState.collectAsState()
    val searchType by viewModel.searchType.collectAsState()
    val query by viewModel.query.collectAsState()

    LaunchedEffect(key1 = searchType, block = {
        viewModel.loadHistory()
    })

    Column(
        modifier = modifier
            .scrollable(
                state = scrollableState,
                orientation = Orientation.Vertical
            )
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Search(
            queryValue = query,
            active = active,
            onActiveChange = {
                active = it
            },
            onQueryChange = {
                viewModel.setQuery(it)
            },
            onSearch = {
                viewModel.addHistory(it)
                viewModel.search(it)
                searchingMode = true
                active = false
            },
            searchHistory = history,
            onRemove = {
                viewModel.deleteHistory(it)
            }
        )

        FilterGroup(
            filters = filters,
            onClickFilter = { index, _ ->
                viewModel.setSearchType(index)
                viewModel.setQuery("")
                searchingMode = false
            },
            selectedFilterItem = searchType.ordinal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp
                )
        )

        if(searchingMode) {
            when(searchType) {
                HistoryType.MOVIE -> {
                    val movies = viewModel.movieState.collectAsLazyPagingItems()

                    MoviesPagingGrid(
                        movies = movies,
                        routeNavigation = routeNavigation,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    )
                }
                HistoryType.PEOPLE -> {

                }
                HistoryType.SERIES -> {
                    val series = viewModel.seriesState.collectAsLazyPagingItems()

                    SeriesPagingGrid(
                        seriesItems = series,
                        routeNavigation = routeNavigation,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Search(
    queryValue: String,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onRemove: (History) -> Unit,
    searchHistory: List<History>
) {
    SearchComponent(
        stringResource(id = R.string.search_hint_text),
        query = queryValue,
        active = active,
        onActiveChange = onActiveChange,
        modifier = Modifier,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch.invoke(it)
        },
        content = {
            LazyColumn(
                content = {
                    items(searchHistory) { history ->
                        HistoryItem(
                            history = history,
                            onClickHistory = {
                                 onSearch.invoke(it.text)
                            },
                            onRemoveHistory = onRemove
                        )
                    }
                },
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
        }
    )
}