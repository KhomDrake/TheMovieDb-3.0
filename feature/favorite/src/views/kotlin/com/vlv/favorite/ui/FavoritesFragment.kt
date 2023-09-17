package com.vlv.favorite.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import br.com.arch.toolkit.delegate.viewProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.favorite.R
import com.vlv.favorite.ui.adapter.FavoritesAdapter

class FavoritesFragment : Fragment(R.layout.favorite_favorites_fragment) {

    private val tabs: TabLayout by viewProvider(R.id.tabs)
    private val content: ViewPager2 by viewProvider(R.id.layout)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf(
            "Movie",
            "Tv show",
            "People"
        )
        items.forEach {
            tabs.addTab(
                tabs.newTab().apply {
                    this.text = it
                }
            )
        }

        val adapter = FavoritesAdapter(items.size, requireActivity())
        content.adapter = adapter
        TabLayoutMediator(tabs, content) { tab, position ->
            tab.text = items[position]
        }.attach()

    }

}