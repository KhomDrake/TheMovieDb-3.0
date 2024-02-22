package com.vlv.people.ui.listing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.people.PeopleListType
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.people.PERSON_CONTENT_TYPE
import com.vlv.common.ui.paging.people.PeoplePagingGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleListingContent(
    routeNavigation: RouteNavigation,
    type: PeopleListType,
    modifier: Modifier = Modifier,
    viewModel: PeopleListingViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = type, block = {
        viewModel.loadSeriesByType(type)
    })

    val state = viewModel.state.collectAsLazyPagingItems()

    PeoplePagingGrid(
        routeNavigation = routeNavigation,
        item = { state[it] },
        loadState = state.loadState,
        itemCount = state.itemCount,
        modifier = modifier,
        itemKey = state.itemKey { people -> people.id },
        itemContentType = state.itemContentType { PERSON_CONTENT_TYPE },
        onRetry = { state.retry() }
    )
}