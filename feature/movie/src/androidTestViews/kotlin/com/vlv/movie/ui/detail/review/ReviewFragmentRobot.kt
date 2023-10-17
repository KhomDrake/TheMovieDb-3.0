package com.vlv.movie.ui.detail.review

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun ReviewFragmentTest.reviewFragment(func: ReviewFragmentSetup.() -> Unit) =
    ReviewFragmentSetup().apply(func)

class ReviewFragmentSetup : Setup<ReviewFragmentLaunch, ReviewFragmentCheck> {
    override fun createCheck(): ReviewFragmentCheck {
        return ReviewFragmentCheck()
    }

    override fun createLaunch(): ReviewFragmentLaunch {
        return ReviewFragmentLaunch()
    }

    override fun setupLaunch() {

    }

}

class ReviewFragmentLaunch : Launch<ReviewFragmentCheck> {
    override fun createCheck(): ReviewFragmentCheck {
        return ReviewFragmentCheck()
    }

}

class ReviewFragmentCheck : Check {

}
