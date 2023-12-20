package com.vlv.movie.presentation.ui.detail.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import com.vlv.movie.presentation.data.MovieDetail

@Composable
fun MovieAbout(
    response: Response<MovieDetail>,
    routeNavigation: RouteNavigation
) {
    response.handle(
        success = {
            MovieAboutSuccess(data = it, modifier = Modifier.fillMaxSize())
        },
        error = {},
        loading = {}
    )
}