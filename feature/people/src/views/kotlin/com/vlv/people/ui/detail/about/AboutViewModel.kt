package com.vlv.people.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.ui.adapter.Information
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.extensions.toLocalDate
import com.vlv.network.data.people.PeopleDetailResponse
import com.vlv.network.repository.PeopleDetailRepository
import com.vlv.people.R

class AboutViewModel(
    private val repository: PeopleDetailRepository
) : ViewModel() {

    fun peopleDetail(resources: Resources, id: Int) = bondsmith<PeopleDetailResponse>()
        .request {
            repository.peopleDetail(id)
        }
        .execute()
        .responseLiveData
        .map { detail ->
            mutableListOf<AboutItem>().apply {
                add(AboutItem.BigText(detail.biography))
                add(AboutItem.Line())
                addAll(
                    listOf(
                        Information(
                            title = R.string.people_information_item_date_of_birth,
                            data = detail.birthday.toLocalDate().toFormattedString(patternDate2())
                        ),
                        Information(
                            title = R.string.people_information_item_place_of_birth,
                            data = detail.placeOfBirth
                        )
                    ).map { AboutItem.InformationItem(it) }
                )
            }
        }
}