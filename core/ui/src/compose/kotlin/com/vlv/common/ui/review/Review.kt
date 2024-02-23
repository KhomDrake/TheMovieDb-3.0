package com.vlv.common.ui.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.vlv.common.data.review.Review
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.ui.R

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
    avatarSize: Dp = 48.dp
) {
    val avatarId = "avatar_id"
    val authorNameId = "author_name_id"
    val createdDateId = "created_date_id"
    val contentId = "content_id"

    val constrains = ConstraintSet {
        val avatarConstraint = createRefFor(avatarId)
        val authorNameConstraint = createRefFor(authorNameId)
        val createdDateConstraint = createRefFor(createdDateId)
        val contentConstraintSet = createRefFor(contentId)

        val bottomBarrier = createBottomBarrier(createdDateConstraint, avatarConstraint, margin = 16.dp)

        constrain(avatarConstraint) {
            top.linkTo(parent.top)
            start.linkTo(parent.start, margin = 16.dp)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(authorNameConstraint) {
            top.linkTo(avatarConstraint.top)
            start.linkTo(avatarConstraint.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(createdDateConstraint) {
            top.linkTo(authorNameConstraint.bottom, margin = 4.dp)
            start.linkTo(avatarConstraint.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(contentConstraintSet) {
            top.linkTo(bottomBarrier)
            start.linkTo(parent.start, margin = 16.dp)
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
            .padding(
                vertical = 12.dp
            )
    ) {
        if(review.url == null) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.image_default),
                contentDescription = stringResource(
                    id = R.string.common_review_avatar_content_description
                ),
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
                model = review.url.toUrlMovieDb(),
                contentDescription = stringResource(
                    id = R.string.common_review_avatar_content_description
                )
            )
        }

        Text(
            modifier = Modifier
                .layoutId(authorNameId),
            text = review.author,
            style = TheMovieDbTypography.ParagraphBoldStyle,
            color = MaterialTheme.colorScheme.onTertiary
        )

        Text(
            modifier = Modifier
                .layoutId(createdDateId),
            text = review.createdAt,
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onTertiary
        )

        Text(
            modifier = Modifier
                .layoutId(contentId),
            text = review.content,
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onTertiary,
            maxLines = 3
        )
    }
}

@PreviewLightDark
@Composable
fun ReviewItemPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            ReviewItem(
                review = Review(
                    id = "asda",
                    author = "Manuel",
                    content = "Life for Peter Parker (Tom Holland) is complicated thanks to his dual life as Spider-Man and the challenges of being in High School. Unfortunately for him; his best intentions are about to make things much worse in “Spider-Man: No Way Home”.\\r\\n\\r\\nTaking place where “Spider-Man: Far From Home” ended; Peter must deal with his secret identity being leaked by Tabloid Journalist J. Jonah Jameson (J.K. Simmons); and the throngs of people, helicopters, and protestors who follow his every move and camp outside his home.\\r\\n\\r\\nAs if this was not bad enough; being accused of being a murderer has drawn the attention of the authorities which further complicates his life as does returning to a school where everyone knows his identity.\\r\\n\\r\\nDesperate to get away from the constant scrutiny and observation; Peter seeks out Doctor Strange (Benedict Cumberbatch), and asks him to cast a spell that would make the world forget that Peter Parker is Spider-Man.\\r\\n\\r\\nStrange agrees but mid-spell Peter requests that there are some exemptions from the spell which include his Girlfriend MJ (Zendaya); his Aunt May (Marisa Tomei); and his friend Ned (Jacob Batalon).\\r\\n\\r\\nStrange agrees but in doing so; complications arise which allows entrants from other dimensions to enter their realm. Soon Peter is accosted by villains whom he does not know but seem to know him; that is until he is unmasked and they have no idea who this Peter Parker is before them.\\r\\n\\r\\nAs more villains arrive; Peter learns of their fates in their natural dimension and is determined to save them and give them a second chance which puts him at odds with Doctor Strange who says they must go back to whatever fate they had.\\r\\n\\r\\nWhat follows is a descent into humor and darkness as Peter despite his best intentions sees the situation go from bad to worse and he must fight to stay true to himself and save the day.\\r\\n\\r\\nThe film is a difficult one to review in the fact that there are so many surprise guests, twists, and turns that it is challenging to not reveal anything but suffice it to say that fans should absolutely enjoy it.\\r\\n\\r\\nThe film takes its time getting to the action as it has a very slow and deliberate climb and Director Jon Watts is confident enough in the characters and premise that he allows ample time for the characters and setting to build and be established before he gets to the action.\\r\\n\\r\\nWhile there is considerable fan service in the film; it never once seems like it is pandering and it all fits very well within the story and the MCU and opens up numerous possibilities for the future.\\r\\n\\r\\nThere is a mid-credit scene and a post-credit scene which is basically a trailer and both are very engaging in terms of the possibilities as Marvel has again shown that their plan of interwoven stories and characters continues to deliver and that Spider-Man still remains as popular and engaging as ever.\\r\\n\\r\\n4 stars out of 5",
                    createdAt = "Thursday, 20 of july of 2023",
                    url = null
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}