package com.vlv.common.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.vlv.common.R
import kotlinx.parcelize.Parcelize
import java.lang.ref.WeakReference
import kotlin.math.abs

@Parcelize
class DetailObject(
    val id: Int,
    val posterPath: String?,
    val backdropPath: String?,
    val title: String,
    val overview: String,
    val adult: Boolean = false
) : Parcelable

const val DETAIL_OBJECT_EXTRA = "DETAIL_OBJECT_EXTRA"
const val DETAIL_SHARED_ELEMENT = "poster"

abstract class DetailActivity : AppCompatActivity(R.layout.common_detail_activity) {

    protected val tabs: TabLayout by viewProvider(R.id.tabs)
    protected val toolbar: Toolbar by viewProvider(R.id.toolbar)
    protected val backdrop: AppCompatImageView by viewProvider(R.id.path)
    protected val collapsing: CollapsingToolbarLayout by viewProvider(R.id.collapsing)
    protected val layout: AutoMeasureViewPager by viewProvider(R.id.layout)
    protected val appBar: AppBarLayout by viewProvider(R.id.app_bar_layout)
    protected val objDetail: DetailObject? by extraProvider(DETAIL_OBJECT_EXTRA, null)

    protected abstract val texts: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finishAfterTransition()
        }

        configTabs()
        configAppBar()
    }

    private fun configTabs() {
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                onSelectedTab(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

        })

        texts.forEach { name ->
            tabs.addTab(
                tabs.newTab().apply {
                    this.text = name
                }
            )
        }
    }

    open fun onSelectedTab(tab: TabLayout.Tab?) = Unit

    protected open fun configAppBar() {
        appBar.addOnOffsetChangedListener(
            AppBarStateChangeListener(
                WeakReference(toolbar)
            ) { _, state ->
                val resource = when(state) {
                    AppBarState.COLLAPSED -> {
                        com.vlv.imperiya.R.color.imperiya_color_primary
                    }
                    AppBarState.FULL_COLLAPSED -> {
                        com.vlv.imperiya.R.color.imperiya_color_background
                    }
                    AppBarState.EXPANDED, AppBarState.IDLE, AppBarState.COLLAPSING -> {
                        com.vlv.imperiya.R.color.imperiya_color_transparent
                    }
                }

                appBar.setStatusBarForegroundResource(resource)
                collapsing.setStatusBarScrimResource(resource)
            })
    }


    @Deprecated("Deprecated in Java", ReplaceWith("finishAfterTransition()"))
    override fun onBackPressed() {
        finishAfterTransition()
    }

}

enum class AppBarState {
    EXPANDED,
    COLLAPSED,
    COLLAPSING,
    FULL_COLLAPSED,
    IDLE
}

class AppBarStateChangeListener(
    private val toolbar: WeakReference<Toolbar>,
    private val onStateChanged: (AppBarLayout, AppBarState) -> Unit
) : AppBarLayout.OnOffsetChangedListener {

    private var currentState = AppBarState.IDLE
    private var toolbarSize = -1

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val absVerticalOffSet = abs(verticalOffset)

        toolbarSize = toolbar.get()?.measuredHeight ?: 0

        val collapsedLimit = (appBarLayout.totalScrollRange - toolbarSize)

        val newState = when (absVerticalOffSet) {
            0 -> {
                AppBarState.EXPANDED;
            }
            collapsedLimit -> {
                AppBarState.COLLAPSED
            }
            appBarLayout.totalScrollRange -> {
                AppBarState.FULL_COLLAPSED
            }
            else -> {
                AppBarState.COLLAPSING
            }
        }

        if(currentState != newState) {
            currentState = newState
            onStateChanged(appBarLayout, newState);
        }
    }

}
