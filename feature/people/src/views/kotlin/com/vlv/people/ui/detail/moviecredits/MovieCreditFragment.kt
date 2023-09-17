package com.vlv.people.ui.detail.moviecredits

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.vlv.common.data.people.People
import com.vlv.common.ui.route.EXTRA_PEOPLE

class MovieCreditFragment : Fragment() {

    companion object {
        fun instance(people: People) = MovieCreditFragment().apply {
            arguments = bundleOf(EXTRA_PEOPLE to people)
        }
    }

}