package com.vlv.tv_show.presentation.ui.detail.seasons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.tv_show.presentation.model.Season
import com.vlv.ui.R

@Composable
fun SeasonItem(
    season: Season,
    modifier: Modifier = Modifier
) {
    val posterId = "poster_id"
    val titleId = "title_id"
    val descriptionId = "description_id"

    val constraintSet = ConstraintSet {
        val posterConstraint = createRefFor(posterId)
        val titleConstraint = createRefFor(titleId)
        val descriptionConstraint = createRefFor(descriptionId)

        constrain(posterConstraint) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)

            height = Dimension.wrapContent
            width = Dimension.wrapContent
        }

        constrain(titleConstraint) {
            top.linkTo(posterConstraint.top, margin = 8.dp)
            start.linkTo(posterConstraint.end, margin = 16.dp)
            end.linkTo(parent.end)

            height = Dimension.wrapContent
            width = Dimension.fillToConstraints
        }

        constrain(descriptionConstraint) {
            top.linkTo(titleConstraint.bottom, margin = 8.dp)
            start.linkTo(posterConstraint.end, margin = 16.dp)
            end.linkTo(parent.end)

            height = Dimension.wrapContent
            width = Dimension.fillToConstraints
        }
    }


    ConstraintLayout(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiaryContainer, RoundedCornerShape(12.dp))
            .padding(12.dp),
        constraintSet = constraintSet
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(.25f)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16)
                )
                .layoutId(posterId)
        ) {
            if(season.poster == null) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.image_default),
                    contentDescription = season.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                )
            } else {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp),
                    model = season.poster.toUrlMovieDb(),
                    contentDescription = season.title
                )
            }
        }

        Text(
            modifier = Modifier
                .layoutId(titleId),
            text = season.title,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier
                .layoutId(descriptionId),
            text = season.description,
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun SeasonItemPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SeasonItem(
                season = Season(
                    2,
                    "Season 1",
                    "8 episodes - Feb/03/2022"
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}