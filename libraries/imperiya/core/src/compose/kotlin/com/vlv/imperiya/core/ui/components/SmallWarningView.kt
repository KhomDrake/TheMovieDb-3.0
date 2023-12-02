package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.preview.PreviewLightDarkWithBackground
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SmallWarningView(
    modifier: Modifier = Modifier,
    title: String?,
    body: String?,
    linkActionText: String,
    showIconInfo: Boolean = true,
    onClickLink: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(16.dp)
            )
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        title?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(showIconInfo) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_info
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = if (showIconInfo) 8.dp else 0.dp),
                    style = TheMovieDbTypography.SubTitleBoldStyle,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        body?.let {
            Text(
                text = body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = if (title.isNullOrEmpty()) 0.dp else 12.dp
                    ),
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Text(
            text = linkActionText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp
                )
                .clickable {
                    onClickLink?.invoke()
                },
            style = TheMovieDbTypography.LinkStyle
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

class SmallWarningViewPreviewData(
    val title: String?,
    val body: String?,
    val linkActionText: String,
    val showIconInfo: Boolean = true
)

class SmallWarningViewProvider : PreviewParameterProvider<SmallWarningViewPreviewData> {
    override val values: Sequence<SmallWarningViewPreviewData>
        get() = listOf(
            SmallWarningViewPreviewData(
                "Failure title you put it here",
                "Failure body you pit here",
                "Text button"
            ),
            SmallWarningViewPreviewData(
                "Failure title you put it here",
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.",
                "Text button"
            ),
            SmallWarningViewPreviewData(
                "Failure title you put it here",
                "Failure body you pit here",
                "Text button",
                showIconInfo = false
            ),
            SmallWarningViewPreviewData(
                null,
                "Failure body you pit here",
                "Text button"
            ),
            SmallWarningViewPreviewData(
                "Failure title you put it here",
                null,
                "Text button"
            )
        ).asSequence()
}

@PreviewLightDarkWithBackground
@Composable
fun SmallWarningViewPreview(
    @PreviewParameter(SmallWarningViewProvider::class) data: SmallWarningViewPreviewData
) {
    TheMovieDbAppTheme {
        SmallWarningView(
            Modifier,
            title = data.title,
            body = data.body,
            linkActionText = data.linkActionText,
            showIconInfo = data.showIconInfo
        )
    }
}

@PreviewFontScale
@Composable
fun SmallWarningViewFontsPreview(
    @PreviewParameter(SmallWarningViewProvider::class) data: SmallWarningViewPreviewData
) {
    TheMovieDbAppTheme(darkTheme = true) {
        SmallWarningView(
            Modifier,
            title = data.title,
            body = data.body,
            linkActionText = data.linkActionText,
            showIconInfo = data.showIconInfo
        )
    }
}
