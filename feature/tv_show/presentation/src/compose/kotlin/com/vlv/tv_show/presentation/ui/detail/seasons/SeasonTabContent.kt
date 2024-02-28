package com.vlv.tv_show.presentation.ui.detail.seasons

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
import com.vlv.common.ui.HorizontalList
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.paging.series.TvShowsEmptyState
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.tv_show.R
import com.vlv.tv_show.presentation.model.Season
import com.vlv.tv_show.presentation.ui.detail.TvShowsScreenError

@Composable
fun SeasonTabContent(
    state: Response<List<Season>>,
    onTryAgain: () -> Unit
) {
    state.handle(
        success = { seasons ->
            if(seasons.isEmpty()) {
                TvShowsEmptyState(
                    title = stringResource(id = R.string.tv_show_empty_season_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            } else {
                HorizontalList(
                    itemCount = seasons.size,
                    content = { index ->
                        val season = seasons[index]
                        SeasonItem(
                            season = season,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    key = { index -> seasons[index].id },
                    contentPaddingValues = PaddingValues(16.dp)
                )
            }
        },
        error = {
            TvShowsScreenError(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                onTryAgain = onTryAgain,
                title = stringResource(id = R.string.tv_show_error_season_title)
            )
        },
        loading = {
            SeasonsShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    )
}

class SeasonTabContentPreviewProvider: PreviewParameterProvider<Response<List<Season>>> {

    override val values: Sequence<Response<List<Season>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(listOf()),
            responseData(
                listOf(
                    Season(2, "Season 1", "8 episodes - Feb/22/2024"),
                    Season(3, "Season 2", "8 episodes - Feb/22/2026"),
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun SeasonTabContentPreview(
    @PreviewParameter(SeasonTabContentPreviewProvider::class) state: Response<List<Season>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SeasonTabContent(
                state = state,
                onTryAgain = {}
            )
        }
    }
}
