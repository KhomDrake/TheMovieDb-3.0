package com.vlv.people.ui.listing

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.people.PeopleListType
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.people.PERSON_CONTENT_TYPE
import com.vlv.common.ui.paging.people.PeoplePagingGrid
import com.vlv.extensions.idInt
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleListingContent(
    paddingValues: PaddingValues,
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
        itemKey = state.itemKey { people -> "${people.id}-${idInt()}" },
        itemContentType = state.itemContentType { PERSON_CONTENT_TYPE },
        onRetry = { state.retry() },
        columns = 2,
        errorPaddingValues = PaddingValues(
            top = paddingValues.calculateTopPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    )
}