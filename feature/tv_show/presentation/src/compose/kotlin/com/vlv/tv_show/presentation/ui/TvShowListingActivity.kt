package com.vlv.tv_show.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.tv_show.TvShowListType
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.TV_SHOW_LISTING_TYPE_EXTRA
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.common.ui.paging.series.TV_SHOW_CONTENT_TYPE
import com.vlv.common.ui.paging.series.TvShowsPagingGrid
import com.vlv.extensions.idInt
import com.vlv.extensions.serializable
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.tv_show.R
import org.koin.androidx.compose.koinViewModel

class TvShowListingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type: TvShowListType = intent.extras
            ?.serializable(TV_SHOW_LISTING_TYPE_EXTRA) ?: TvShowListType.TRENDING

        setContent {
            TheMovieDbThemeWithDynamicColors {
                Scaffold(
                    topBar = {
                        val name = when(type) {
                            TvShowListType.TRENDING -> R.string.tv_show_trending_title
                            TvShowListType.AIRING_TODAY -> R.string.tv_show_airing_today_title
                            TvShowListType.POPULAR -> R.string.tv_show_popular_title
                            TvShowListType.ON_THE_AIR -> R.string.tv_show_on_the_air_title
                            TvShowListType.TOP_RATED -> R.string.tv_show_top_rated_title
                        }
                        DefaultTopBar(title = stringResource(id = name)) {
                            finish()
                        }
                    }
                ) {
                    TvShowsContent(
                        paddingValues = it,
                        routeNavigation = { route, data ->
                            handleRoute(route, data)
                        },
                        tvShowListType = type
                    )
                }
            }
        }
    }

}

@Composable
fun TvShowsContent(
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation,
    tvShowListType: TvShowListType,
    viewModel: TvShowListingViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = tvShowListType, block = {
        viewModel.series(tvShowListType)
    })

    val tvShows = viewModel.state.collectAsLazyPagingItems()

    TvShowsPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        loadStates = tvShows.loadState,
        itemCount = tvShows.itemCount,
        itemKey = tvShows.itemKey { item -> "${item.id}-${idInt()}" },
        itemContentType = tvShows.itemContentType { TV_SHOW_CONTENT_TYPE },
        item = { index -> tvShows[index] },
        onRetry = {
            tvShows.retry()
        },
        errorPaddingValues = PaddingValues(
            top = paddingValues.calculateTopPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp
        )
    )

}