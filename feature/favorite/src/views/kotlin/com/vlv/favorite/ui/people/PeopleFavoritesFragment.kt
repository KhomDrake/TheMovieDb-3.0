package com.vlv.favorite.ui.people

import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vlv.common.ui.adapter.people.PeopleAdapter
import com.vlv.common.ui.route.toPeopleDetail
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.favorite.R
import com.vlv.favorite.ui.BaseFavoriteFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFavoritesFragment : BaseFavoriteFragment() {

    private val viewModel: PeopleFavoritesViewModel by viewModel()

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_people_listing_loading

    override fun setupStateView() {
        emptyState.setStateIcon(com.vlv.imperiya.core.R.drawable.ic_review)
        emptyState.setTitle(R.string.favorite_people_empty_state)
    }

    override fun setupRecyclerView() {
        items.layoutManager = GridLayoutManager(requireContext(), 3)
        items.adapter = PeopleAdapter { people, view ->
            val intent = requireContext().toPeopleDetail(
                people,
                finishAfterTransition = false
            )
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    view,
                    getString(com.vlv.ui.R.string.common_avatar_transition_name)
                ).toBundle()
            )
        }
    }

    override fun loadFavorites() {
        viewModel.peopleFavorites().observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState() else viewStateMachine.dataState()

                (items.adapter as? PeopleAdapter)?.submitList(it)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { e ->
                viewStateMachine.errorState()
            }
        }
    }


}