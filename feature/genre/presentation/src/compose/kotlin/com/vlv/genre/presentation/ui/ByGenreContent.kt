package com.vlv.genre.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.vlv.common.ui.extension.handle
import com.vlv.genre.R
import com.vlv.genre.presentation.data.Genre
import com.vlv.genre.presentation.ui.widget.GenreTabsShimmer
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.components.WarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun ByGenreContent(
    state: Response<List<Genre>>,
    tabContent: @Composable (Genre) -> Unit,
    paddingValues: PaddingValues,
    tryAgain: () -> Unit
) {
    state.handle(
        success = { genres ->
            if(genres.isNotEmpty()) {
                ByGenreSuccess(
                    paddingValues = paddingValues,
                    genres = genres,
                    genresContent = tabContent
                )
            } else {
                WarningView(
                    title = stringResource(id = R.string.genre_error_general),
                    body = stringResource(id = com.vlv.ui.R.string.common_error_description),
                    linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button),
                    onClickLink = tryAgain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = paddingValues.calculateTopPadding()
                        ),
                    showCloseIcon = false
                )
            }
        },
        error = {
            WarningView(
                title = stringResource(id = R.string.genre_error_general),
                body = stringResource(id = com.vlv.ui.R.string.common_error_description),
                linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button),
                onClickLink = tryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = paddingValues.calculateTopPadding()
                    ),
                showCloseIcon = false
            )
        },
        loading = {
            GenreTabsShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            )
        }
    )
}

class GenresContentProvider: PreviewParameterProvider<Response<List<Genre>>> {

    override val values: Sequence<Response<List<Genre>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(listOf()),
            responseData(
                listOf(
                    Genre(
                        1,
                        "Fantasy"
                    ),
                    Genre(
                        2,
                        "Sci-fi"
                    ),
                    Genre(
                        3,
                        "Romance"
                    ),
                    Genre(
                        4,
                        "Historic"
                    ),
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun ByGenreContentPreview(
    @PreviewParameter(GenresContentProvider::class) data: Response<List<Genre>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            ByGenreContent(
                state = data,
                tabContent = {
                    StateView(
                        icon = com.vlv.imperiya.core.R.drawable.ic_hearts,
                        title = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                },
                paddingValues = PaddingValues(),
                tryAgain = {}
            )
        }
    }
}
