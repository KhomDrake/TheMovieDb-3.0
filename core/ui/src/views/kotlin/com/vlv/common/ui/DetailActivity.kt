package com.vlv.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.vlv.common.route.FINISH_AFTER_TRANSITION_EXTRA
import com.vlv.common.route.DETAIL_OBJECT_EXTRA
import com.vlv.extensions.getAttrColor
import com.vlv.extensions.getAttrColorResourceId
import com.vlv.ui.R
import java.lang.ref.WeakReference
import kotlin.math.abs

abstract class DetailActivity : AppCompatActivity(R.layout.common_detail_activity) {

    protected val tabs: TabLayout by viewProvider(R.id.tabs)
    protected val toolbar: Toolbar by viewProvider(R.id.toolbar)
    protected val backdrop: AppCompatImageView by viewProvider(R.id.backdrop)
    protected val poster: AppCompatImageView by viewProvider(R.id.poster)
    protected val expandedTitle: AppCompatTextView by viewProvider(R.id.expanded_title)
    protected val expandedScore: AppCompatTextView by viewProvider(R.id.score)
    protected val expandedDateAndTime: AppCompatTextView by viewProvider(R.id.expanded_date_and_time)
    protected val collapsing: CollapsingToolbarLayout by viewProvider(R.id.collapsing)
    protected val layout: ViewPager2 by viewProvider(R.id.layout)
    protected val appBar: AppBarLayout by viewProvider(R.id.app_bar_layout)
    protected val objDetail: DetailObject? by extraProvider(DETAIL_OBJECT_EXTRA, null)
    protected val finishAfterTransition: Boolean?
        by extraProvider(FINISH_AFTER_TRANSITION_EXTRA, true)

    protected abstract val texts: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            if(finishAfterTransition == true) {
                finishAfterTransition()
            } else finish()
        }
        toolbar.navigationContentDescription = getString(R.string.common_back_content_description)

        poster.clipToOutline = true

        showExpanded()
        configTabs()
        configAppBar()
    }

    private fun configTabs() {
        texts.forEach { name ->
            tabs.addTab(
                tabs.newTab().apply {
                    this.text = name
                }
            )
        }
    }

    protected open fun configAppBar() {
        appBar.addOnOffsetChangedListener(
            AppBarStateChangeListener(
                WeakReference(toolbar)
            ) { _, state ->
                val color = when(state) {
                    AppBarState.COLLAPSED -> {
                        getAttrColor(org.koin.android.R.attr.colorPrimary)
                    }
                    AppBarState.FULL_COLLAPSED -> {
                        ContextCompat.getColor(
                            this,
                            com.vlv.imperiya.core.R.color.color_imperiya_background
                        )
                    }
                    AppBarState.EXPANDED, AppBarState.IDLE, AppBarState.COLLAPSING -> {
                        ContextCompat.getColor(
                            this,
                            com.vlv.imperiya.core.R.color.imperiya_color_transparent
                        )
                    }
                }

                when(state) {
                    AppBarState.EXPANDED, AppBarState.COLLAPSING -> showExpanded()
                    AppBarState.COLLAPSED -> showCollapsed(fullCollapsed = false)
                    AppBarState.FULL_COLLAPSED -> showCollapsed(fullCollapsed = true)
                    else -> Unit
                }

                appBar.setStatusBarForegroundColor(color)
                collapsing.setContentScrimColor(color)
            })
    }

    open fun showCollapsed(fullCollapsed: Boolean) {
        val obj = objDetail ?: return
        collapsing.title = obj.title
        expandedTitle.text = obj.title
        collapsing.isInvisible = fullCollapsed
        expandedTitle.isInvisible = true
        expandedScore.isInvisible = true
        expandedDateAndTime.isInvisible = true
    }

    open fun showExpanded() {
        val obj = objDetail ?: return
        collapsing.title = null
        collapsing.isInvisible = false
        expandedTitle.text = obj.title
        expandedTitle.isVisible = true
        expandedScore.isVisible = expandedScore.text.isNullOrBlank().not()
        expandedDateAndTime.isVisible = expandedDateAndTime.text.isNullOrBlank().not()
    }

    open fun showExpandedInfo(scoreText: String?, dateAndTimeText: String?) {
        expandedScore.text = scoreText
        expandedScore.contentDescription = getString(
            R.string.common_expanded_score,
            scoreText
        )
        expandedDateAndTime.text = dateAndTimeText
        expandedScore.isVisible = expandedScore.text.isNullOrBlank().not()
        expandedDateAndTime.isVisible = expandedDateAndTime.text.isNullOrBlank().not()
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finishAfterTransition()"))
    override fun onBackPressed() {
        if(finishAfterTransition == true) {
            finishAfterTransition()
        } else finish()
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
