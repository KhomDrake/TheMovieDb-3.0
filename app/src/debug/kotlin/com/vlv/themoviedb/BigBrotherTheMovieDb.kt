package com.vlv.themoviedb

import br.com.mrocigno.bigbrother.core.BigBrotherProvider
import br.com.mrocigno.bigbrother.log.addLogPage
import br.com.mrocigno.bigbrother.network.addNetworkPage

class BigBrotherTheMovieDb : BigBrotherProvider() {
    override val isEnabled: Boolean
        get() = true

    override fun setupPages() {
        addLogPage()
        addNetworkPage()
    }
}