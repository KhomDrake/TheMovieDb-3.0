package com.vlv.movie.presentation.ui.detail.cast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.vlv.common.data.cast.Cast
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.cast.CastList
import com.vlv.common.ui.cast.CastShimmer
import com.vlv.common.ui.extension.handle
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.movie.R
import com.vlv.movie.presentation.ui.detail.MovieScreenError

@Composable
fun MovieCastContent(
    state: Response<List<Cast>>,
    routeNavigation: RouteNavigation,
    tryAgain: () -> Unit
) {
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
            MovieScreenError(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                title = stringResource(id = R.string.movie_error_cast_title),
                onTryAgain = tryAgain
            )
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

class MovieCastContentPreviewProvider: PreviewParameterProvider<Response<List<Cast>>> {

    override val values: Sequence<Response<List<Cast>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(
                listOf(
                    Cast(
                        1,
                        "Bea",
                        2,
                        "Sydney Sweeney",
                        "",
                        "",
                        null
                    ),
                    Cast(
                        2,
                        "Ben",
                        3,
                        "Glen Powell",
                        "",
                        "",
                        null
                    ),
                    Cast(
                        3,
                        "Claudia",
                        4,
                        "Alexandra Shipp",
                        "",
                        "",
                        null
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun MovieCastContentPreview(
    @PreviewParameter(MovieCastContentPreviewProvider::class) data: Response<List<Cast>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieCastContent(
                state = data,
                routeNavigation = {_, _ ->},
                tryAgain = {}
            )
        }
    }
}
