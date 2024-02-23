package com.vlv.search.ui.bytype

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.movie.MOVIE_CONTENT_TYPE
import com.vlv.common.ui.paging.movie.MovieEmptyState
import com.vlv.common.ui.paging.movie.MoviesPagingGrid
import com.vlv.common.ui.paging.people.PERSON_CONTENT_TYPE
import com.vlv.common.ui.paging.people.PeopleEmptyState
import com.vlv.common.ui.paging.people.PeoplePagingGrid
import com.vlv.common.ui.paging.series.SERIES_CONTENT_TYPE
import com.vlv.common.ui.paging.series.SeriesEmptyState
import com.vlv.common.ui.paging.series.SeriesPagingGrid
import com.vlv.data.database.data.HistoryType
import com.vlv.search.R

@Composable
fun SearchByType(
    historyType: HistoryType,
    movieState: LazyPagingItems<Movie>,
    seriesState: LazyPagingItems<Series>,
    personState: LazyPagingItems<People>,
    routeNavigation: RouteNavigation
) {
    when(historyType) {
        HistoryType.MOVIE -> {
            MoviesPagingGrid(
                itemCount = movieState.itemCount,
                item = { index -> movieState[index] },
                itemKey = movieState.itemKey { movie -> movie.apiId },
                itemContentType = movieState.itemContentType { MOVIE_CONTENT_TYPE },
                loadStates = movieState.loadState,
                routeNavigation = routeNavigation,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                emptyState = {
                    MovieEmptyState(
                        title = stringResource(id = R.string.search_movie_empty_title),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            )
        }
        HistoryType.PEOPLE -> {
            PeoplePagingGrid(
                routeNavigation = routeNavigation,
                item = { personState[it] },
                loadState = personState.loadState,
                itemCount = personState.itemCount,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                itemKey = personState.itemKey { people -> people.id },
                itemContentType = personState.itemContentType { PERSON_CONTENT_TYPE },
                onRetry = { personState.retry() },
                columns = 2,
                emptyState = {
                    PeopleEmptyState(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        title = stringResource(id = R.string.search_people_empty_title)
                    )
                }
            )
        }
        HistoryType.SERIES -> {
            SeriesPagingGrid(
                routeNavigation = routeNavigation,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                loadStates = seriesState.loadState,
                itemCount = seriesState.itemCount,
                itemKey = seriesState.itemKey { item -> item.id },
                itemContentType = seriesState.itemContentType { item -> SERIES_CONTENT_TYPE },
                item = { index -> seriesState[index] },
                onRetry = {
                    seriesState.retry()
                },
                emptyState = {
                    SeriesEmptyState(
                        title = stringResource(id = R.string.search_series_empty_title),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            )
        }
    }
}