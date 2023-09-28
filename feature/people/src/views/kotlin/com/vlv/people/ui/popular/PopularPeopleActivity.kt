package com.vlv.people.ui.popular

import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.ui.adapter.people.PeopleLoaderAdapter
import com.vlv.common.ui.adapter.people.PeoplePagingAdapter
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.common.ui.route.toPeopleDetail
import com.vlv.people.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularPeopleActivity : ListingItemsActivity() {

    private val viewModel: PopularViewModel by viewModel()

    override val adapter: PagingDataAdapter<*, *>
        get() = pagingAdapter

    private val pagingAdapter = PeoplePagingAdapter { image, people ->
        startActivity(
            toPeopleDetail(people),
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                image,
                getString(com.vlv.common.R.string.common_avatar_transition_name)
            ).toBundle()
        )
    }

    override val loadingLayout: Int
        get() = com.vlv.common.R.layout.common_people_listing_loading

    override val title: Int
        get() = R.string.people_popular_title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.popular().distinctUntilChanged().apply {
                collectLatest {
                    pagingAdapter.submitData(it)
                }
            }
        }
    }

    override fun configWarningView() {
        warningView.apply {
            setTitle(com.vlv.common.R.string.common_error_title)
            setBody(com.vlv.common.R.string.common_error_description)
            setButtonText(com.vlv.common.R.string.common_error_button)
            setCloseIcon {
                finish()
            }
            setOnTryAgain {
                pagingAdapter.retry()
            }
        }
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(this, 3)
    }

    override fun loaderAdapter() = PeopleLoaderAdapter {
        pagingAdapter.retry()
    }

}