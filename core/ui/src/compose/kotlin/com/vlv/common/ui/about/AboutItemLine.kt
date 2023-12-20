package com.vlv.common.ui.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun AboutItemLine(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(2.dp)
            .background(
                MaterialTheme.colorScheme.outline
            )
    ) {

    }
}

@PreviewLightDark
@Composable
fun AboutItemLinePreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
            ) {
                AboutItemLine()
            }
        }
    }
}