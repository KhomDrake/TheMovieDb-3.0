package com.vlv.movie.presentation.ui.detail.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.movie.presentation.data.PillItem

@Composable
fun AboutItemPills(
    items: List<PillItem>,
    paddingValues: PaddingValues = PaddingValues(),
    modifier: Modifier = Modifier
) {
    LazyRow(
        content = {
            items(items) { item: PillItem ->
                Pill(item = item)
            }
        },
        contentPadding = paddingValues,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    )
}

@Composable
fun Pill(
    item: PillItem
) {
    Text(
        text = item.title,
        style = TheMovieDbTypography.ParagraphStyle,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
    )
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun AboutItemPillsPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutItemPills(
                items = listOf(
                    PillItem(
                        1, "Action"
                    ),
                    PillItem(
                        2, "Comedy"
                    ),
                    PillItem(
                        3, "Fantasy"
                    ),
                )
            )
        }
    }
}