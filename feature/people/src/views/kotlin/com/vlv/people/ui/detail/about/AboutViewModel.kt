package com.vlv.people.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.ui.adapter.Information
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
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

                val items = mutableListOf<Information>().apply {
                    detail.birthday?.let {
                        add(
                            Information(
                                title = R.string.people_information_item_date_of_birth,
                                data = it.toFormattedString(patternDate2())
                            )
                        )
                    }
                    detail.placeOfBirth?.let {
                        Information(
                            title = R.string.people_information_item_place_of_birth,
                            data = it
                        )
                    }
                }

                addAll(items.map { AboutItem.InformationItem(it) })
            }
        }
}