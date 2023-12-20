package com.vlv.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.common.data.cast.Cast
import com.vlv.common.ui.cast.CastItem
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun HorizontalList(
    itemCount: Int,
    content: @Composable (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    titleTextStyle: TextStyle = TheMovieDbTypography.SubTitleBoldStyle,
    title: String? = null
) {
    LazyColumn(
        content = {
            title?.let {
                item {
                    Text(
                        text = title,
                        style = titleTextStyle,
                        color = titleColor
                    )
                }
            }

            items(itemCount) { index ->
                content.invoke(index)
            }
        },
        modifier = modifier,
        contentPadding = contentPaddingValues,
        verticalArrangement = verticalArrangement
    )
}

@PreviewLightDark
@Composable
fun HorizontalListPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            HorizontalList(
                itemCount = 6,
                title = "Seasons 6",
                content = {
                    CastItem(
                        cast = Cast(
                            castId = 2 * it,
                            character = "J. Robert Oppenheimer $it",
                            personId = 2 * it,
                            name = "Cillian Murphy $it",
                            originalName = "Cillian Murphy $it",
                            knowFor = "Actor",
                            profilePath = null
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                titleTextStyle = TheMovieDbTypography.TitleStyle
            )
        }
    }
}