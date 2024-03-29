package com.vlv.people.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlv.common.data.people.People
import com.vlv.people.ui.detail.about.AboutFragment
import com.vlv.people.ui.detail.moviecredits.MovieCreditFragment
import com.vlv.people.ui.detail.seriescredit.SeriesCreditFragment

class PeopleDetailAdapter(
    private val itemsQuantity: Int,
    private val people: People,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = itemsQuantity

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AboutFragment.instance(people)
            1 -> MovieCreditFragment.instance(people)
            else -> SeriesCreditFragment.instance(people)
        }
    }
}