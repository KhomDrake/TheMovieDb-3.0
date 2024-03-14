package com.vlv.tv_show.presentation.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.DETAIL_OBJECT_EXTRA
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.extensions.parcelable
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import org.koin.androidx.compose.koinViewModel

class TvShowDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbThemeWithDynamicColors {
                val series = intent.extras?.parcelable<DetailObject>(DETAIL_OBJECT_EXTRA)
                    ?: return@TheMovieDbThemeWithDynamicColors finish()
                TvShowDetailScreen(
                    series = series,
                    onBackButton = {
                        finish()
                    }
                ) { route: ScreenRoute, data: Any? ->
                    handleRoute(route, data)
                }
            }
        }
    }

}

@Composable
fun TvShowDetailScreen(
    series: DetailObject,
    onBackButton: () -> Unit,
    routeNavigation: RouteNavigation
) {
    Scaffold(
        topBar = {
            TopBar(series = series, onBackButton = onBackButton)
        }
    ) {
        TvShowsDetailContent(
            detailObject = series,
            paddingValues = it,
            routeNavigation = routeNavigation
        )
    }
}

@Composable
fun TopBar(
    series: DetailObject,
    onBackButton: () -> Unit,
    viewModel: TvShowsDetailViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = series.id, block = {
        viewModel.isFavorite(series)
    })

    val isFavorite by viewModel.favoriteState.collectAsState()

    DefaultTopBar(
        title = series.title,
        actions = {
            IconButton(onClick = {
                viewModel.changeFavorite(series, isFavorite)
            }) {
                Icon(
                    painter = painterResource(
                        id = if(isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_enable
                    ),
                    contentDescription = stringResource(
                        id = if(isFavorite) com.vlv.tv_show.R.string.tv_show_is_favorite
                        else com.vlv.tv_show.R.string.tv_show_is_not_favorite
                    ),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        onBackButton = {
            onBackButton.invoke()
        }
    )
}