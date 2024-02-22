package com.vlv.people.ui.listing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.data.people.PeopleListType
import com.vlv.common.route.RouteNavigation
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


}