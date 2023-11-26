package com.vlv.series.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.series.SeriesListType
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.SERIES_LISTING_TYPE_EXTRA
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.SeriesPoster
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.common.ui.shimmer.SinglePosterShimmer
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.series.R
import org.koin.androidx.compose.koinViewModel

class SeriesListingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type: SeriesListType = intent.extras
            ?.getSerializable(
                SERIES_LISTING_TYPE_EXTRA, SeriesListType::class.java
            ) ?: SeriesListType.TRENDING

        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        val name = when(type) {
                            SeriesListType.TRENDING -> R.string.series_trending_title
                            SeriesListType.AIRING_TODAY -> R.string.series_airing_today_title
                            SeriesListType.POPULAR -> R.string.series_popular_title
                            SeriesListType.ON_THE_AIR -> R.string.series_on_the_air_title
                            SeriesListType.TOP_RATED -> R.string.series_top_rated_title
                        }
                        DefaultTopBar(title = stringResource(id = name)) {
                            finish()
                        }
                    }
                ) {
                    SeriesContent(
                        paddingValues = it,
                        routeNavigation = { route, data ->
                            handleRoute(route, data)
                        },
                        seriesListType = type
                    )
                }
            }
        }
    }

}

@Composable
fun SeriesContent(
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation,
    seriesListType: SeriesListType,
    viewModel: SeriesListingViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = seriesListType, block = {
        viewModel.series(seriesListType)
    })

    val seriesState = viewModel.state.collectAsLazyPagingItems()

    if(seriesState.loadState.refresh is LoadState.Loading) {
        GridPosterShimmer(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding()),
            count = 4,
            height = 200.dp
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding()
                ),
            columns = GridCells.Fixed(2),
            content = {
                items(
                    count = seriesState.itemCount,
                    key = seriesState.itemKey { series -> series.id },
                    contentType = seriesState.itemContentType { "Poster" }
                ) { index ->
                    seriesState[index]?.let { series ->
                        SeriesPoster(
                            series = series,
                            height = 200.dp,
                            onRouteNavigation = routeNavigation
                        )
                    }
                }

                item {
                    when(seriesState.loadState.append) {
                        is LoadState.Loading -> {
                            SinglePosterShimmer(
                                modifier = Modifier.fillMaxWidth(),
                                height = 200.dp
                            )
                        }
                        is LoadState.Error -> {
                            // one item error
                        }
                        else -> Unit
                    }
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