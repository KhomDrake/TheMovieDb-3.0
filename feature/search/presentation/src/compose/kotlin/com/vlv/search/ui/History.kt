package com.vlv.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ItemType
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.search.R

@Composable
fun HistoryItem(
    history: History,
    onClickHistory: (History) -> Unit,
    onRemoveHistory: (History) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onClickHistory.invoke(history)
            }
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .padding(
                    end = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
        )
        Text(
            text = history.text,
            color = MaterialTheme.colorScheme.onTertiary,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            modifier = Modifier
                .weight(2f)
        )
        IconButton(onClick = { onRemoveHistory.invoke(history) }) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(
                    id = R.string.search_remove_search_icon_description
                ),
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }


    }

}

@PreviewLightDark
@Composable
fun HistoryItemPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview(background = MaterialTheme.colorScheme.tertiary) {
            HistoryItem(
                history = History("Text", ItemType.MOVIE),
                onClickHistory = {

                },
                onRemoveHistory = {

                }
            )
        }
    }
}
