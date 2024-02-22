package com.vlv.common.ui.grid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.common.data.people.People
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.people.PeopleEmptyState
import com.vlv.common.ui.poster.PeoplePoster
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PeopleGrid(
    data: List<People>,
    routeNavigation: RouteNavigation,
    emptyStateTitle: String,
    modifier: Modifier = Modifier,
    columns: Int = 2,
) {
    if(data.isEmpty()) {
        PeopleEmptyState(
            modifier = Modifier
                .fillMaxWidth(),
            title = emptyStateTitle
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(columns),
            content = {
                items(
                    data,
                    key = { people -> people.id }
                ) { people ->
                    PeoplePoster(
                        people = people,
                        size = 128.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                        onRouteNavigation = routeNavigation
                    )
                }
            },
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        )
    }
}

@PreviewLightDark
@Composable
fun PeopleGridPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            PeopleGrid(
                data = listOf(
                    People(
                        1,
                        "Person 1",
                        ""
                    ),
                    People(
                        1,
                        "Person 2",
                        ""
                    ),
                    People(
                        1,
                        "Person 3",
                        ""
                    ),
                    People(
                        1,
                        "Person 4",
                        ""
                    ),
                    People(
                        1,
                        "Person 5",
                        ""
                    )
                ),
                routeNavigation = {_,_ ->},
                emptyStateTitle = "text"
            )
        }
    }
}
