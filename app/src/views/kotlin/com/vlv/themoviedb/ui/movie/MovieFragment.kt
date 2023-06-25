package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.vlv.themoviedb.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(R.layout.movie_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().apply {
            addOrReplace(
                childFragmentManager,
                R.id.content,
                TrendingNowFragment(),
                TrendingNowFragment::class.java.name
            )
            addOrReplace(
                childFragmentManager,
                R.id.content,
                NowPlayingFragment(),
                NowPlayingFragment::class.java.name
            )
        }.commit()
    }

}

fun FragmentTransaction.addOrReplace(
    fragmentManager: FragmentManager,
    @IdRes layout: Int,
    fragment: Fragment,
    tag: String
) {
    fragmentManager.findFragmentByTag(tag)?.let {
        remove(it)
    }
    add(layout, fragment, tag)
}