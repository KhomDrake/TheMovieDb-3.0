package com.vlv.tv_show.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.data.series.TvShowListType
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.TV_SHOW_LISTING_TYPE_EXTRA
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.tv_show.R
import org.koin.androidx.compose.koinViewModel

class TvShowListingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type: TvShowListType = intent.extras
            ?.getSerializable(
                TV_SHOW_LISTING_TYPE_EXTRA, TvShowListType::class.java
            ) ?: TvShowListType.TRENDING

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

    val seriesState = viewModel.state.collectAsLazyPagingItems()

//    TvShowsPagingGrid(
//        seriesItems = seriesState,
//        routeNavigation = routeNavigation,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = paddingValues.calculateTopPadding())
//    )

}