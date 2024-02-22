package com.vlv.common.ui.paging.people

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.data.people.People
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.PeoplePoster
import com.vlv.common.ui.extension.isFullLoading
import com.vlv.common.ui.extension.isSingleError
import com.vlv.common.ui.extension.isSingleLoading
import com.vlv.common.ui.extension.stateData
import com.vlv.common.ui.extension.stateFullLoading
import com.vlv.common.ui.extension.stateSingleError
import com.vlv.common.ui.extension.stateSingleLoading
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.common.ui.shimmer.SinglePersonShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun PeoplePagingGrid(
    routeNavigation: RouteNavigation,
    item: (Int) -> People?,
    loadState: CombinedLoadStates,
    itemCount: Int,
    modifier: Modifier = Modifier,
    itemKey: ((index: Int) -> Any)? = null,
    itemContentType: (index: Int) -> Any? = { null },
    heightItem: Dp = 200.dp,
    itemCountInitialLoading: Int = 4,
    columns: Int = 3,
    emptyState: @Composable () -> Unit = {
        PeopleEmptyState(
            modifier = Modifier
                .fillMaxWidth()
        )
    },
    onRetry: () -> Unit = {}
) {
    if(loadState.isFullLoading()) {
        GridPosterShimmer(
            modifier = modifier,
            count = itemCountInitialLoading,
            height = heightItem
        )
    } else {
        if(itemCount == 0) {
            emptyState.invoke()
            return
        }

        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(columns),
            content = {
                items(
                    count = itemCount,
                    key = itemKey,
                    contentType = itemContentType
                ) { index ->
                    item.invoke(index)?.let { people ->
                        PeoplePoster(
                            people = people,
                            onRouteNavigation = routeNavigation
                        )
                    }
                }

                when {
                    loadState.isSingleLoading() -> {
                        item(key = ResponseStatus.LOADING) {
                            SinglePersonShimmer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            )
                        }

                    }
                    loadState.isSingleError() -> {
                       item(
                           key = ResponseStatus.ERROR,
                           span = {
                               GridItemSpan(maxLineSpan)
                           }
                       ) {
                           PeopleErrorState(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(top = 8.dp),
                               onClickLink = {
                                    onRetry.invoke()
                               }
                           )
                       }
                    }
                    else -> Unit
                }
            },
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        )
    }
}

data class PeoplePagingPreviewData(
    val loadState: CombinedLoadStates = stateData(),
    val itemCount: Int = 0,
    val items: List<People> = listOf()
)

class PeoplePagingPreviewProvider: PreviewParameterProvider<PeoplePagingPreviewData> {

    override val values: Sequence<PeoplePagingPreviewData>
        get() = listOf<PeoplePagingPreviewData>(
            PeoplePagingPreviewData(
                loadState = stateFullLoading()
            ),
            PeoplePagingPreviewData(
                loadState = stateData(),
                itemCount = 0
            ),
            PeoplePagingPreviewData(
                loadState = stateData(),
                itemCount = 5,
                items = listOf(
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
                    ),
                )
            ),
            PeoplePagingPreviewData(
                loadState = stateSingleLoading(),
                itemCount = 3,
                items = listOf(
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
                    )
                )
            ),
            PeoplePagingPreviewData(
                loadState = stateSingleError(),
                itemCount = 3,
                items = listOf(
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
                    )
                )
            ),
        )
            .asSequence()

}

@PreviewLightDark
@Composable
fun PeoplePaging(
    @PreviewParameter(PeoplePagingPreviewProvider::class) data: PeoplePagingPreviewData
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            PeoplePagingGrid(
                routeNavigation = {_, _ ->},
                modifier = Modifier
                    .fillMaxSize(),
                item = { index ->
                    data.items[index]
                },
                itemCount = data.itemCount,
                loadState = data.loadState,
                emptyState = {
                    PeopleEmptyState(
                        modifier = Modifier
                            .fillMaxWidth(),
                        title = "No people was found"
                    )
                }
            )
        }
    }
}


