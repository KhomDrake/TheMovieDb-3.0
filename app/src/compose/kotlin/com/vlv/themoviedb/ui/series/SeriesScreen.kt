package com.vlv.themoviedb.ui.series

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.data.series.Series
import com.vlv.common.route.toSeriesSearch
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.theme.Link
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.series.widget.SeriesCarousel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesScreen(
    onIntent: (Intent) -> Unit
) {
    val seriesSearch = LocalContext.current.toSeriesSearch()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchComponent(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Search for series",
            onFocus = {
                onIntent.invoke(seriesSearch)
            }
        )
        SeriesTrending(
            onIntent = {
//            onIntent.invoke()
            }
        )
        AiringToday(
            onIntent = {

            }
        )
    }
}

@Composable
fun AiringToday(
    viewModel: AiringTodayViewModel = koinViewModel(),
    onIntent: (Series) -> Unit
) {
    val airingToday by viewModel.state.collectAsState()

    SeriesInformation(
        title = stringResource(id = R.string.airing_today_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_series_airing_today),
        data = airingToday,
        onIntent = onIntent,
        titleWarning = stringResource(id = R.string.error_series_load_text_title_airing_today),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button)
    )

}

@Composable
fun SeriesTrending(
    viewModel: SeriesTrendingViewModel = koinViewModel(),
    onIntent: (Series) -> Unit
) {
    val trending by viewModel.state.collectAsState()

    SeriesInformation(
        title = stringResource(id = R.string.trending_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_series_trending),
        data = trending,
        onIntent = onIntent,
        titleWarning = stringResource(id = R.string.error_series_load_text_title_trending),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button)
    )
}

@Composable
fun SeriesInformation(
    title: String,
    emptyStateTitle: String,
    titleWarning: String,
    bodyWarning: String,
    linkTextWarning: String,
    data: Response<List<Series>>,
    onIntent: (Series) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = title,
            style = TheMovieDbTypography.TitleStyle,
            color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = stringResource(id = R.string.see_all_text),
            style = TheMovieDbTypography.LinkStyle,
            color = Link
        )
    }


    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val series = data.data ?: return
            SeriesCarousel(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                series = series,
                emptyStateTitle = emptyStateTitle,
                onClickSeries = {
                    onIntent.invoke(it)
                }
            )
        }
        ResponseStatus.LOADING -> {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = "LOADING",
                style = TheMovieDbTypography.TitleBigStyle,
                color = Link
            )
        }
        ResponseStatus.ERROR -> {
            SmallWarningView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                title = titleWarning,
                body = bodyWarning,
                linkActionText = linkTextWarning
            )
        }
        else -> Unit
    }
}
