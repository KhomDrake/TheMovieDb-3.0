package com.vlv.common.ui.cast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.vlv.common.data.cast.Cast
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.common.route.ScreenRoute
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.ui.R

@Composable
fun CastItem(
    cast: Cast,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 48.dp,
    onClickCast: (Cast) -> Unit = {}
) {
    val avatarId = "avatar_id"
    val actorNameId = "actor_name_id"
    val characterNameId = "character_name_id"

    val constrains = ConstraintSet {
        val avatarConstraint = createRefFor(avatarId)
        val actorConstraint = createRefFor(actorNameId)
        val characterConstraint = createRefFor(characterNameId)

        constrain(avatarConstraint) {
            top.linkTo(parent.top)
            start.linkTo(parent.start, margin = 16.dp)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(actorConstraint) {
            top.linkTo(avatarConstraint.top, margin = 4.dp)
            start.linkTo(avatarConstraint.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(characterConstraint) {
            top.linkTo(actorConstraint.bottom, margin = 4.dp)
            start.linkTo(avatarConstraint.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(16.dp)
            )
            .clickable {
                onClickCast.invoke(cast)
            }
            .padding(
                vertical = 12.dp
            )
    ) {
        if(cast.profilePath == null) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.image_default),
                contentDescription = "Cast: ${cast.name}",
                modifier = Modifier
                    .layoutId(avatarId)
                    .size(avatarSize)
            )
        } else {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .layoutId(avatarId)
                    .clip(
                        RoundedCornerShape(avatarSize / 2)
                    )
                    .background(
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(avatarSize / 2)
                    )
                    .size(avatarSize),
                model = cast.profilePath.toUrlMovieDb(),
                contentDescription = "Cast: ${cast.name}"
            )
        }

        Text(
            modifier = Modifier
                .layoutId(actorNameId),
            text = cast.name,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            color = MaterialTheme.colorScheme.onTertiary
        )

        Text(
            modifier = Modifier
                .layoutId(characterNameId),
            text = cast.character,
            style = TheMovieDbTypography.SubTitleStyle,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun CastItemPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            CastItem(
                cast = Cast(
                    castId = 2,
                    character = "J. Robert Oppenheimer",
                    personId = 2,
                    name = "Cillian Murphy",
                    originalName = "Cillian Murphy",
                    knowFor = "Actor",
                    profilePath = null
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}