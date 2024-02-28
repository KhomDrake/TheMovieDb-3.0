package com.vlv.tv_show.presentation.ui.detail.about

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
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.common.data.about.PillItem
import com.vlv.common.ui.about.AboutList
import com.vlv.common.ui.about.AboutListShimmer
import com.vlv.common.ui.extension.handle
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.tv_show.R
import com.vlv.tv_show.presentation.model.TvShowDetail
import com.vlv.tv_show.presentation.ui.detail.TvShowsScreenError

@Composable
fun AboutTvShowsContent(
    state: Response<TvShowDetail>,
    onTryAgain: () -> Unit
) {
    state.handle(
        success = {
            AboutList(
                items = it.aboutItems,
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
                title = stringResource(id = R.string.tv_show_error_detail_title)
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

class AboutTvShowsContentPreviewProvider: PreviewParameterProvider<Response<TvShowDetail>> {

    override val values: Sequence<Response<TvShowDetail>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(
                TvShowDetail(
                    "",
                    "",
                    listOf(
                        AboutItem.BigText(text= "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man."),
                        AboutItem.Title(R.string.tv_show_title_genres),
                        AboutItem.Genres(
                            listOf(
                                PillItem(2, "Comedy"),
                                PillItem(4, "Romance")
                            )
                        ),
                        AboutItem.Title(R.string.tv_show_title_last_episode),
                        AboutItem.Episode(
                            "S1E8 - Feb/22/2024",
                            "Legends"
                        ),
                        AboutItem.Title(R.string.tv_show_title_next_episode),
                        AboutItem.Episode(
                            "S2E1 - Feb/22/2026",
                            "Avatar state"
                        ),
                        AboutItem.Line(),
                        AboutItem.Title(R.string.tv_show_title_information),
                        AboutItem.InformationItem(
                            Information(title = R.string.tv_show_information_original_title, data = "Anyone but you")
                        )
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun AboutTvShowsContentPreview(
    @PreviewParameter(AboutTvShowsContentPreviewProvider::class) data: Response<TvShowDetail>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutTvShowsContent(
                state = data,
                onTryAgain = {}
            )
        }
    }
}
