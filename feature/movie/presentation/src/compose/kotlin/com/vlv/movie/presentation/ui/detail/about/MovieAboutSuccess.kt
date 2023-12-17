package com.vlv.movie.presentation.ui.detail.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.movie.R
import com.vlv.movie.presentation.data.AboutItem
import com.vlv.movie.presentation.data.Information
import com.vlv.movie.presentation.data.MovieDetail
import com.vlv.movie.presentation.data.PillItem

@Composable
fun MovieAboutSuccess(
    data: MovieDetail,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        content = {
            items(data.items) { item: AboutItem ->
                when(item) {
                    is AboutItem.Line -> {
                        AboutItemLine(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 4.dp
                                )
                        )
                    }
                    is AboutItem.Title -> {
                        AboutItemTitle(
                            item = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 8.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        )
                    }
                    is AboutItem.InformationItem -> {
                        AboutItemInformation(
                            item = item,
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        )
                    }
                    is AboutItem.Genres -> {
                        AboutItemPills(
                            items = item.pillItems,
                            paddingValues = PaddingValues(
                                top = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                        )
                    }
                    is AboutItem.BigText -> {
                        AboutItemBigText(
                            item = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 8.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        )
                    }
                }
            }
        },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 12.dp)
    )
}

@PreviewLightDark
@Composable
fun MovieAboutSuccessPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieAboutSuccess(
                data = MovieDetail(
                    id = 2,
                    items = listOf(
                        AboutItem.BigText("", "akjsdhsajklhdkjadhask"),
                        AboutItem.Title(R.string.movie_title_genres),
                        AboutItem.Genres(
                            listOf(
                                PillItem(
                                    2, "Krimi"
                                ),
                                PillItem(
                                    3, "Drama"
                                ),
                                PillItem(
                                    4, "Historia"
                                ),
                            )
                        ),
                        AboutItem.Line(),
                        AboutItem.Title(R.string.movie_title_information),
                        AboutItem.InformationItem(
                            Information(
                                title = R.string.movie_text_original_title,
                                data = "Killers of the Flower Moon"
                            )
                        ),
                        AboutItem.InformationItem(
                            Information(
                                title = R.string.movie_text_duration,
                                data = "3h 26min"
                            )
                        )
                    ),
                    score = "",
                    dateAndTime = ""
                )
            )
        }
    }
}