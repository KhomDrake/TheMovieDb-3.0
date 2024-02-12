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
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.MoviesPagingGrid
import com.vlv.data.database.data.History
import com.vlv.data.database.data.HistoryType
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
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
    var query by remember {
        mutableStateOf("")
    }
    var search by remember {
        mutableStateOf(false)
    }

    val history by viewModel.historyState.collectAsState()
    val movies = viewModel.movieState.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadHistory(historyType = HistoryType.MOVIE)
    })

    Column(
        modifier = modifier
            .scrollable(
                state = scrollableState,
                orientation = Orientation.Vertical
            )
            .padding(
                top = 16.dp
            )
    ) {
        Search(
            queryValue = query,
            active = active,
            onActiveChange = {
                active = it
            },
            onQueryChange = {
                query = it
            },
            onSearch = {
                viewModel.addHistory(
                    History(it, HistoryType.MOVIE)
                )
                viewModel.movies(it)
                search = true
                active = false
            },
            searchHistory = history
        )

        if(search) {
            MoviesPagingGrid(
                movies = movies,
                routeNavigation = routeNavigation,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            )
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
    searchHistory: List<History>
) {
    SearchComponent(
        "Search for",
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
                    items(searchHistory) {
                        Text(
                            text = it.text,
                            color = MaterialTheme.colorScheme.onError,
                            style = TheMovieDbTypography.SubTitleBoldStyle,
                            modifier = Modifier
                                .clickable {
                                    onSearch.invoke(it.text)
                                }
                                .padding(12.dp)
                                .fillMaxWidth()
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