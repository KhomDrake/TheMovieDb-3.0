package com.vlv.people.ui.search

import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.ui.adapter.LoaderAdapter
import com.vlv.common.ui.adapter.people.PeopleLoaderAdapter
import com.vlv.common.ui.adapter.people.PeoplePagingAdapter
import com.vlv.common.route.toPeopleDetail
import com.vlv.common.ui.search.SearchActivity
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.data.network.database.data.History
import com.vlv.people.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchPeopleActivity : SearchActivity() {

    private val viewModel: SearchPeopleViewModel by viewModel()

    private val pagingAdapter = PeoplePagingAdapter { image, people ->
        startActivity(
            toPeopleDetail(people),
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                image,
                getString(com.vlv.ui.R.string.common_avatar_transition_name)
            ).toBundle()
        )
    }

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_people_listing_loading
    override val titleHistoryTitle: Int
        get() = R.string.people_search_history_title
    override val searchHint: Int
        get() = R.string.people_search_hint_title
    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    override fun onTextSubmit(query: String?) {
        lifecycleScope.launch {
            val textQuery = query ?: return@launch
            viewModel.searchPeople(textQuery).distinctUntilChanged().apply {
                collectLatest {
                    (pagingAdapter as? PeoplePagingAdapter)?.submitData(it)
                }
            }
        }
    }

    override fun addHistory(text: String?) {
        text?.let {
            viewModel.addToHistory(it)
        }
    }

    override fun removeHistory(history: History) {
        viewModel.removeHistory(history)
    }

    override fun loadHistory() {
        viewModel.historyBySearchType(getString(titleHistoryTitle)).observe(this) {
            updateHistory(it)
        }
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 3)
    }

    override fun loaderAdapter(): LoaderAdapter {
        return PeopleLoaderAdapter {
            pagingAdapter.retry()
        }
    }

    override fun configStateView(stateView: StateView) {
        stateView
            .setStateIcon(com.vlv.imperiya.core.R.drawable.ic_people)
            .setTitle(R.string.people_search_empty_title)

    }

    override fun configWarningView(smallWarningView: SmallWarningView) = Unit
}