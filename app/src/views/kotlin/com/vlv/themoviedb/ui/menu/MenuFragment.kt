package com.vlv.themoviedb.ui.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.movie.ui.adapter.VIEW_TYPE_MOVIE
import com.vlv.themoviedb.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment : Fragment(R.layout.menu_fragment) {

    private val viewModel: MenuViewModel by viewModel()

    private val menuItems: RecyclerView by viewProvider(R.id.menu_items)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MenuAdapter {

        }
        val layoutManager = GridLayoutManager(
            requireContext(),
            3,
            GridLayoutManager.VERTICAL,
            false
        ).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = adapter.getItemViewType(position)

                    return if (viewType == MenuItemType.ITEM.ordinal) 1 else 3
                }

            }
        }
        menuItems.layoutManager = layoutManager
        menuItems.adapter = adapter

        viewModel.menuItems().observe(viewLifecycleOwner) {
            data {
                adapter.submitList(it)
            }
        }
    }

}