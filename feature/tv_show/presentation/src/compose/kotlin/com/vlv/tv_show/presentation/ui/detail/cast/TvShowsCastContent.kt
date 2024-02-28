package com.vlv.tv_show.presentation.ui.detail.cast

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
import com.vlv.tv_show.R
import com.vlv.tv_show.presentation.ui.detail.TvShowsScreenError

@Composable
fun TvShowsCastContent(
    state: Response<List<Cast>>,
    routeNavigation: RouteNavigation,
    onTryAgain: () -> Unit
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
            TvShowsScreenError(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                onTryAgain = onTryAgain,
                title = stringResource(id = R.string.tv_show_error_cast_title)
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

class TvShowsCastContentPreviewProvider: PreviewParameterProvider<Response<List<Cast>>> {

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
fun TvShowsCastContentPreview(
    @PreviewParameter(TvShowsCastContentPreviewProvider::class) data: Response<List<Cast>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowsCastContent(
                state = data,
                routeNavigation = {_,_ ->},
                onTryAgain = {}
            )
        }
    }
}
