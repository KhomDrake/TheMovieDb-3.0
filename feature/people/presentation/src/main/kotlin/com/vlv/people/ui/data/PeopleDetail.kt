package com.vlv.people.ui.data

import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.data.common.model.people.PeopleDetailResponse
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.people.R

class PeopleDetail(
    val about: List<AboutItem>
) {
    constructor(response: PeopleDetailResponse) : this(
        response.run {
            val about = mutableListOf<AboutItem>()
            about.add(
                AboutItem.BigText(
                    null, response.biography
                )
            )

            about.add(AboutItem.Line())

            response.birthday?.toFormattedString(patternDate2())?.let {
                about.add(
                    AboutItem.InformationItem(
                    Information(
                        title = R.string.people_information_item_date_of_birth,
                        data = it
                    )
                ))
            }

            response.deathday?.toFormattedString(patternDate2())?.let {
                about.add(
                    AboutItem.InformationItem(
                    Information(
                        title = R.string.people_information_item_date_of_death,
                        data = it
                    )
                ))
            }

            response.placeOfBirth?.let {
                about.add(
                    AboutItem.InformationItem(
                    Information(
                        title = R.string.people_information_item_place_of_birth,
                        data = it
                    )
                ))
            }

            about
        }
    )
}