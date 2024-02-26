package com.vlv.search.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.route.RouteNavigation
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ItemType
import com.vlv.imperiya.core.ui.components.FilterGroup
import com.vlv.imperiya.core.ui.components.FilterItemData
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.search.R
import com.vlv.search.ui.bytype.SearchByType
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    filters: List<FilterItemData>,
    hint: @Composable (ItemType) -> String = {stringResource(id = R.string.search_hint_text)},
    defaultSearchType: ItemType? = null,
    viewModel: SearchViewModel = koinViewModel()
) {
    var active by rememberSaveable {
        mutableStateOf(false)
    }
    var searchingMode by rememberSaveable {
        mutableStateOf(false)
    }

    val history by viewModel.historyState.collectAsState()
    val searchType by viewModel.searchType.collectAsState()
    val query by viewModel.query.collectAsState()

    LaunchedEffect(key1 = searchType, block = {
        viewModel.loadHistory()
    })

    LaunchedEffect(key1 = Unit, block = {
        defaultSearchType?.let { viewModel.setSearchType(it) }
    })

    LaunchedEffect(key1 = Unit, block = {
        if(searchingMode && !active) {
            viewModel.search()
        }
    })

    Column(
        modifier = modifier
            .padding(top = 16.dp)
    ) {
        SearchWithHistory(
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
                .padding(horizontal = 16.dp),
            hint = hint.invoke(searchType)
        )

        if(filters.isNotEmpty()) {
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
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            )
        }

        if(searchingMode) {
            SearchByType(
                historyType = searchType,
                routeNavigation = routeNavigation,
                movieState = viewModel.movieState.collectAsLazyPagingItems(),
                tvShowState = viewModel.tvShowState.collectAsLazyPagingItems(),
                personState = viewModel.peopleState.collectAsLazyPagingItems(),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchWithHistory(
    queryValue: String,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onRemove: (History) -> Unit,
    searchHistory: List<History>,
    modifier: Modifier = Modifier,
    hint: String = stringResource(id = R.string.search_hint_text),
) {
    SearchComponent(
        hint = hint,
        query = queryValue,
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch.invoke(it)
        },
        content = {
            LazyColumn(
                content = {
                    items(
                        searchHistory,
                        key = { item -> item.toString() }
                    ) { history ->
                        HistoryItem(
                            history = history,
                            onClickHistory = {
                                 onSearch.invoke(it.text)
                            },
                            onRemoveHistory = onRemove,
                            modifier = Modifier
                                .animateItemPlacement()
                        )
                    }
                },
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 4.dp,
                    top = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            )
        }
    )
}