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
import com.vlv.common.ui.extension.isFullError
import com.vlv.common.ui.extension.isFullLoading
import com.vlv.common.ui.extension.isSingleError
import com.vlv.common.ui.extension.isSingleLoading
import com.vlv.common.ui.extension.stateData
import com.vlv.common.ui.extension.stateFullError
import com.vlv.common.ui.extension.stateFullLoading
import com.vlv.common.ui.extension.stateSingleError
import com.vlv.common.ui.extension.stateSingleLoading
import com.vlv.common.ui.poster.PeoplePoster
import com.vlv.common.ui.shimmer.GridPersonShimmer
import com.vlv.common.ui.shimmer.SinglePersonShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

const val PERSON_CONTENT_TYPE = "PERSON"

@Composable
fun PeoplePagingGrid(
    routeNavigation: RouteNavigation,
    item: (Int) -> People?,
    loadState: CombinedLoadStates,
    itemCount: Int,
    modifier: Modifier = Modifier,
    itemKey: ((index: Int) -> Any)? = null,
    itemContentType: (index: Int) -> Any? = { null },
    itemCountInitialLoading: Int = 9,
    columns: Int = 3,
    emptyState: @Composable () -> Unit = {
        PeopleEmptyState(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    },
    errorPaddingValues: PaddingValues = PaddingValues(16.dp),
    onRetry: () -> Unit = {},
    itemSize: Dp = 128.dp
) {
    when {
        loadState.isFullLoading() -> {
            GridPersonShimmer(
                modifier = modifier,
                count = itemCountInitialLoading,
                columns = columns,
                size = itemSize
            )
        }
        loadState.isFullError() -> {
            PeopleErrorState(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(errorPaddingValues),
                onClickLink = {
                    onRetry.invoke()
                }
            )
        }
        else -> {
            if(itemCount == 0) {
                emptyState.invoke()
            } else {
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
                                    onRouteNavigation = routeNavigation,
                                    size = itemSize
                                )
                            }
                        }

                        when {
                            loadState.isSingleLoading() -> {
                                item(
                                    key = ResponseStatus.LOADING,
                                    contentType = ResponseStatus.LOADING
                                ) {
                                    SinglePersonShimmer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        size = itemSize
                                    )
                                }
                            }

                            loadState.isSingleError() -> {
                                item(
                                    key = ResponseStatus.ERROR,
                                    span = {
                                        GridItemSpan(maxLineSpan)
                                    },
                                    contentType = ResponseStatus.ERROR
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
                loadState = stateFullError()
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
fun PeoplePagingPreview(
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
                            .fillMaxWidth()
                            .padding(16.dp),
                        title = "No people was found"
                    )
                }
            )
        }
    }
}


