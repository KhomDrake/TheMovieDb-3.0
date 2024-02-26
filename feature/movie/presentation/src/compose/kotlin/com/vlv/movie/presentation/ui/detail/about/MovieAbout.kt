package com.vlv.movie.presentation.ui.detail.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.common.data.about.PillItem
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.about.AboutList
import com.vlv.common.ui.about.AboutListShimmer
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.paging.movie.MovieErrorState
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.movie.R
import com.vlv.movie.presentation.data.MovieDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieAbout(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: MovieAboutViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.movieDetail(detailObject.id)
    })

    val state by viewModel.aboutState.collectAsState()

    MovieAboutContent(
        state = state,
        onTryAgain = {
            viewModel.movieDetail(detailObject.id)
        }
    )
}

@Composable
fun MovieAboutContent(
    state: Response<MovieDetail>,
    onTryAgain: () -> Unit
) {
    state.handle(
        success = { data ->
            AboutList(
                items = data.items,
                modifier = Modifier
                    .fillMaxSize()
            )
        },
        error = {
            MovieErrorState(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                title = stringResource(id = R.string.movie_error_detail_title),
                onTryAgain = onTryAgain
            )
        },
        loading = {
            AboutListShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    )
}

class MovieAboutContentPreviewProvider: PreviewParameterProvider<Response<MovieDetail>> {

    override val values: Sequence<Response<MovieDetail>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(
                MovieDetail(
                    2,
                    listOf(
                        AboutItem.BigText(text= "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man."),
                        AboutItem.Title(R.string.movie_title_genres),
                        AboutItem.Genres(
                            listOf(
                                PillItem(2, "Comedy"),
                                PillItem(4, "Romance")
                            )
                        ),
                        AboutItem.Line(),
                        AboutItem.Title(R.string.movie_title_information),
                        AboutItem.InformationItem(
                            Information(title = R.string.movie_text_original_title, data = "Anyone but you")
                        ),
                        AboutItem.InformationItem(
                            Information(title = R.string.movie_text_duration, data = "1h 40min")
                        )
                    ),
                    "",
                    ""
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun MovieAboutContentPreview(
    @PreviewParameter(MovieAboutContentPreviewProvider::class) data: Response<MovieDetail>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieAboutContent(
                state = data,
                onTryAgain = {}
            )
        }
    }
}
