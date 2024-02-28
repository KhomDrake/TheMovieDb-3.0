package com.vlv.common.ui.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.common.data.about.PillItem
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun AboutList(
    items: List<AboutItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        content = {
            items(
                items,
                key = { item -> item.id }
            ) { item: AboutItem ->
                when(item) {
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
                    is AboutItem.Episode -> {
                        AboutItemEpisode(
                            episode = item,
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
                    else -> {
                        AboutItemLine(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 4.dp
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
fun AboutListPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutList(
                items = listOf(
                    AboutItem.BigText("", "akjsdhsajklhdkjadhask"),
                    AboutItem.Title(R.string.common_open_movie_detail),
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
                    AboutItem.Title(R.string.common_open_movie_detail),
                    AboutItem.Episode(
                        title = "Picture Says a Thousand Words",
                        description = "S2E4 - Dec/21/2023",
                        poster = null
                    ),
                    AboutItem.Title(R.string.common_open_movie_detail),
                    AboutItem.Episode(
                        title = "Picture Says a Thousand Words",
                        description = "S2E4 - Dec/21/2023",
                        poster = null
                    ),
                    AboutItem.Line(),
                    AboutItem.Title(R.string.common_error_button_load),
                    AboutItem.InformationItem(
                        Information(
                            title = R.string.common_open_series_detail,
                            data = "Killers of the Flower Moon"
                        )
                    ),
                    AboutItem.InformationItem(
                        Information(
                            title = R.string.common_open_people_detail,
                            data = "3h 26min"
                        )
                    )
                )
            )
        }
    }
}
