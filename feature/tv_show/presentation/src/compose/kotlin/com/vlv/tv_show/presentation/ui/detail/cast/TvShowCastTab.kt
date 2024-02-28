package com.vlv.tv_show.presentation.ui.detail.cast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.cast.CastList
import com.vlv.common.ui.cast.CastShimmer
import com.vlv.common.ui.extension.handle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowCastTab(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: TvShowCastViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.cast(detailObject.id)
    })

    state.handle(
        success = {
            CastList(
                castItems = it,
                routeNavigation = routeNavigation,
                modifier = Modifier
                    .fillMaxSize()
            )
        },
        error = {

        },
        loading = {
            CastShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    )

}