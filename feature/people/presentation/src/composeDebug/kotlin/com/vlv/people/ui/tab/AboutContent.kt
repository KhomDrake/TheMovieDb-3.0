package com.vlv.people.ui.tab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.common.data.people.People
import com.vlv.common.ui.about.AboutList
import com.vlv.common.ui.about.AboutListShimmer
import com.vlv.common.ui.extension.handle
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.people.R
import com.vlv.people.ui.data.PeopleDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutContent(
    people: People,
    modifier: Modifier = Modifier,
    viewModel: PeopleAboutViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = people.id, block = {
        viewModel.detail(people)
    })

    val state by viewModel.detailState.collectAsState()

    AboutContentStates(
        state = state,
        tryAgain = {
            viewModel.detail(people)
        },
        modifier = modifier
    )
}

@Composable
fun AboutContentStates(
    state: Response<PeopleDetail>,
    modifier: Modifier = Modifier,
    tryAgain: () -> Unit = {}
) {
    state.handle(
        success = {
            AboutList(
                items = it.about,
                modifier = modifier
            )
        },
        loading = {
            AboutListShimmer(
                modifier = modifier
            )
        },
        error = {
            SmallWarningView(
                title = stringResource(id = com.vlv.ui.R.string.common_error_title),
                body = stringResource(id = com.vlv.ui.R.string.common_error_description),
                linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button),
                onClickLink = {
                    tryAgain.invoke()
                }
            )
        }
    )
}


class AboutContentStatesProvider: PreviewParameterProvider<Response<PeopleDetail>> {

    override val values: Sequence<Response<PeopleDetail>>
        get() = listOf(
            responseLoading(),
            responseError(null),
            responseData(
                PeopleDetail(
                    about = listOf(
                        AboutItem.BigText(
                            null, "Hailee Steinfeld (born December 11, 1996) is an American actress and singer. Known for her acting versatility and musical prowess, she is the recipient of various accolades, including a Peabody Award, a Critics' Choice Movie Award, a Billboard Music Award, and nominations for an Academy Award, a British Academy Film Award, a Golden Globe Award, three Critics' Choice Movie Awards and a Screen Actors Guild Award.\\n\\nShe had her breakthrough with the western drama film True Grit (2010), which earned her Academy Award and Screen Actors Guild Award nominations for Best Supporting Actress, and a BAFTA Award nomination for Best Actress. Steinfeld then rose to mainstream prominence for her lead roles in Ender's Game (2013), Romeo & Juliet (2013), Begin Again (2013), and 3 Days to Kill (2014). She received critical acclaim for her roles in the Pitch Perfect film series (2015–2017) and the coming-of-age comedy-drama film The Edge of Seventeen (2016), the latter of which earned her a Golden Globe Award and Critics' Choice Movie Award nomination.\\n\\nSteinfeld has since provided the voice of Gwen Stacy / Spider-Woman in the animated film Spider-Man: Into the Spider-Verse (2018) and Vi in Netflix's animated TV series Arcane (2021–present), based on the League of Legends video game franchise. She also starred as Charlie Watson in the Transformers film Bumblebee (2018) and Emily Dickinson in the Apple TV+ comedy-drama series Dickinson (2019–2021). She played Kate Bishop / Hawkeye, appearing in the 2021 Disney+ series Hawkeye, set in the Marvel Cinematic Universe.\\n\\nSteinfeld gained recognition in music after performing \\\"Flashlight\\\" in Pitch Perfect 2 (2015). She signed with Republic Records soon after and released her debut single, \\\"Love Myself\\\", followed by her debut extended play Haiz (2015). She went on to release a series of successful singles, including \\\"Starving\\\", \\\"Most Girls\\\" and \\\"Let Me Go\\\". In 2020, she released her second extended play, Half Written Story.\\n\\nDescription above from the Wikipedia article Hailee Steinfeld, licensed under CC-BY-SA, full list of contributors on Wikipedia."
                        ),
                        AboutItem.Line(),
                        AboutItem.InformationItem(
                            Information(
                                title = R.string.people_information_item_date_of_birth,
                                data = "1996-12-11"
                            )
                        ),
                        AboutItem.InformationItem(
                            Information(
                                title = R.string.people_information_item_date_of_death,
                                data = "2016-12-11"
                            )
                        ),
                        AboutItem.InformationItem(
                            Information(
                                title = R.string.people_information_item_place_of_birth,
                                data = "Tarzana - Los Angeles - California - USA"
                            )
                        )
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun AboutContentStatesPreview(
    @PreviewParameter(AboutContentStatesProvider::class) data: Response<PeopleDetail>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutContentStates(
                state = data,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}