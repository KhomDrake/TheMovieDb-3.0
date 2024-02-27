package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

class TextPreviewData(
    val textStyle: TextStyle,
    val name: String,
)

class TextProvider : PreviewParameterProvider<TextPreviewData> {

    override val values: Sequence<TextPreviewData>
        get() = listOf(
            TextPreviewData(
                TheMovieDbTypography.ParagraphStyle,
                "ParagraphStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.ParagraphBoldStyle,
                "ParagraphBoldStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.ParagraphBigStyle,
                "ParagraphBigStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.LinkStyle,
                "LinkStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.ChipStyle,
                "ChipStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.SubTitleStyle,
                "SubTitleStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.SubTitleSmallStyle,
                "SubTitleSmallStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.SubTitleBigStyle,
                "SubTitleBigStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.SubTitleBoldStyle,
                "SubTitleBoldStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.TitleStyle,
                "TitleStyle"
            ),
            TextPreviewData(
                TheMovieDbTypography.TitleBigStyle,
                "TitleBigStyle"
            ),
        ).asSequence()

}

@PreviewLightDark
@Composable
fun TextsPreview(
    @PreviewParameter(TextProvider::class) data: TextPreviewData
) {
    TheMovieDbAppTheme {
        Text(
            text = data.name,
            style = data.textStyle,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}
