package com.vlv.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.SEARCH_TYPE_EXTRA
import com.vlv.common.route.ScreenRoute
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.data.database.data.HistoryType
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.search.R
import com.vlv.search.ui.bytype.SearchByType
import org.koin.androidx.compose.koinViewModel

class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val type = intent.extras?.getString(SEARCH_TYPE_EXTRA)
            val searchType = HistoryType.values().find {
                it.name == type
            } ?: HistoryType.MOVIE
            TheMovieDbThemeWithDynamicColors {
                SearchScreen(
                    defaultSearchType = searchType,
                    routeNavigation = { route: ScreenRoute, data: Any? ->
                        handleRoute(route, data)
                    },
                    filters = listOf(),
                    modifier = Modifier
                        .fillMaxSize(),
                    hint = {
                        when(it) {
                            HistoryType.MOVIE -> {
                                stringResource(id = R.string.search_movie_hint_text)
                            }
                            HistoryType.SERIES -> {
                                stringResource(id = R.string.search_series_hint_text)
                            }
                            HistoryType.PEOPLE -> {
                                stringResource(id = R.string.search_people_hint_text)
                            }
                        }
                    }
                )
            }
        }
    }

}

@Composable
fun SearchContentType(
    searchType: HistoryType,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel()
) {
    var active by remember {
        mutableStateOf(false)
    }
    var searchingMode by rememberSaveable {
        mutableStateOf(false)
    }

    val query by viewModel.query.collectAsState()
    val history by viewModel.historyState.collectAsState()

    LaunchedEffect(key1 = searchType, block = {
        viewModel.setSearchType(searchType.ordinal)
        viewModel.loadHistory()
    })

    LaunchedEffect(key1 = Unit, block = {
        if(searchingMode && !active) {
            viewModel.search()
        }
    })

    Column(
        modifier = modifier
            .padding(
                top = 16.dp
            )
    ) {
        SearchWithHistory(
            hint = when(searchType) {
                HistoryType.MOVIE -> {
                    stringResource(id = R.string.search_movie_hint_text)
                }
                HistoryType.SERIES -> {
                    stringResource(id = R.string.search_series_hint_text)
                }
                HistoryType.PEOPLE -> {
                    stringResource(id = R.string.search_people_hint_text)
                }
            },
            queryValue = query,
            active = active,
            onActiveChange = {
                active = it
            },
            onQueryChange = {
                viewModel.setQuery(it)
            },
            onSearch = {
                viewModel.search(it)
                searchingMode = true
                active = false
            },
            searchHistory = history,
            onRemove = {
                viewModel.deleteHistory(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        if(searchingMode) {
            SearchByType(
                historyType = searchType,
                movieState = viewModel.movieState.collectAsLazyPagingItems(),
                seriesState = viewModel.seriesState.collectAsLazyPagingItems(),
                personState = viewModel.peopleState.collectAsLazyPagingItems(),
                routeNavigation = routeNavigation
            )
        }
    }
}