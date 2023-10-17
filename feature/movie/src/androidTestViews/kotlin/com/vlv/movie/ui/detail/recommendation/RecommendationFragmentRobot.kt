package com.vlv.movie.ui.detail.recommendation

import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup

fun RecommendationFragmentTest.recommendationFragment(func: RecommendationFragmentSetup.() -> Unit) =
    RecommendationFragmentSetup().apply(func)

class RecommendationFragmentSetup : Setup<RecommendationFragmentLaunch, RecommendationFragmentCheck> {
    override fun createCheck(): RecommendationFragmentCheck {
        return RecommendationFragmentCheck()
    }

    override fun createLaunch(): RecommendationFragmentLaunch {
        return RecommendationFragmentLaunch()
    }

    override fun setupLaunch() {

    }

}

class RecommendationFragmentLaunch : Launch<RecommendationFragmentCheck> {
    override fun createCheck(): RecommendationFragmentCheck {
        return RecommendationFragmentCheck()
    }

}

class RecommendationFragmentCheck : Check {

}
