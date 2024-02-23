package com.vlv.people.ui.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.data.people.People
import com.vlv.common.route.RouteNavigation
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.people.R
import com.vlv.people.ui.detail.tab.AboutContent
import com.vlv.people.ui.detail.tab.MoviesCreditContent
import com.vlv.people.ui.detail.tab.TvShowsCreditContent
import kotlinx.coroutines.launch

enum class PeopleDetailPage(@StringRes val pageTitle: Int) {
    ABOUT(R.string.people_tab_about),
    MOVIES_CREDIT(R.string.people_tab_movie_credit),
    TV_SHOWS_CREDIT(R.string.people_tab_series_credit)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PeopleContent(
    people: People,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val tabs = PeopleDetailPage.values()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(
        modifier = modifier
    ) {
        TabRow(
            itemCount = tabs.size,
            currentIndex = pagerState.currentPage,
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(it)
                }
            }
        ) { index, isSelected ->
            val item = tabs[index]
            TabItem(name = stringResource(id = item.pageTitle), isSelected = isSelected)
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            when(tabs[index]) {
                PeopleDetailPage.ABOUT -> {
                    AboutContent(
                        people = people,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                PeopleDetailPage.MOVIES_CREDIT -> {
                    MoviesCreditContent(
                        people = people,
                        routeNavigation = routeNavigation,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                PeopleDetailPage.TV_SHOWS_CREDIT -> {
                    TvShowsCreditContent(
                        people = people,
                        routeNavigation = routeNavigation,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}