package com.vlv.favorite.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.people.People
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.PeoplePoster
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.paging.people.PeopleEmptyState
import com.vlv.common.ui.paging.people.PeopleErrorState
import com.vlv.common.ui.shimmer.GridPersonShimmer
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.peopleState.collectAsState()

    LaunchedEffect(
        key1 = Unit,
        block = {
            favoritesViewModel.peopleFavorites()
        }
    )

    PeopleFavoritesContent(
        state = state,
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        onTryAgain = {
            favoritesViewModel.peopleFavorites()
        }
    )
}

@Composable
fun PeopleFavoritesContent(
    state: Response<List<People>>,
    routeNavigation: RouteNavigation,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier,
    columns: Int = 2
) {
    val lazyGridState = rememberLazyGridState()

    Box(
        modifier = modifier
    ) {
        state.handle(
            success = { data ->
                if(data.isEmpty()) {
                    PeopleEmptyState(
                        modifier = Modifier
                            .fillMaxWidth(),
                        title = stringResource(id = R.string.favorite_people_empty_state)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        content = {
                            items(data) { people ->
                                PeoplePoster(
                                    people = people,
                                    size = 128.dp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onRouteNavigation = routeNavigation
                                )
                            }
                        },
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        state = lazyGridState
                    )
                }
            },
            error = {
                PeopleErrorState(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClickLink = onTryAgain
                )
            },
            loading = {
                GridPersonShimmer(
                    columns = columns,
                    size = 128.dp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPaddingValues = PaddingValues()
                )
            }
        )
    }
}

class PeopleFavoritesContentPreviewProvider : PreviewParameterProvider<Response<List<People>>> {

    override val values: Sequence<Response<List<People>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(listOf()),
            responseData(
                listOf(
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
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun PeopleFavoriteContentPrev(
    @PreviewParameter(PeopleFavoritesContentPreviewProvider::class) data: Response<List<People>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            PeopleFavoritesContent(
                state = data,
                routeNavigation = { _, _ ->

                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                onTryAgain = {

                }
            )
        }
    }
}