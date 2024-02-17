package com.vlv.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.SEARCH_TYPE_EXTRA
import com.vlv.common.route.ScreenRoute
import com.vlv.common.route.handleRoute
import com.vlv.data.database.data.HistoryType
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.search.R
import org.koin.androidx.compose.koinViewModel

class SearchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val type = intent.extras?.getString(SEARCH_TYPE_EXTRA)
            val searchType = HistoryType.values().find {
                it.name == type
            } ?: HistoryType.MOVIE
            TheMovieDbAppTheme {
                SearchContentType(
                    searchType = searchType,
                    routeNavigation = { route: ScreenRoute, data: Any? ->
                        handleRoute(route, data)
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
    val query by remember {
        mutableStateOf("")
    }
    val scrollableState = rememberScrollState()
    var active by remember {
        mutableStateOf(false)
    }
    var searchingMode by remember {
        mutableStateOf(false)
    }
    val history by viewModel.historyState.collectAsState()

    LaunchedEffect(key1 = searchType, block = {
        viewModel.setSearchType(searchType.ordinal)
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
        SearchWithHistory(
            hint = when(searchType) {
                HistoryType.MOVIE -> {
                    stringResource(id = R.string.search_movie_hint_text)
                }
                HistoryType.SERIES -> {
                    stringResource(id = R.string.search_series_hint_text)
                }
                else -> ""
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

        if(searchingMode) {
            SearchByType(
                historyType = searchType,
                movieState = viewModel.movieState,
                seriesState = viewModel.seriesState,
                routeNavigation = routeNavigation
            )
        }
    }
}