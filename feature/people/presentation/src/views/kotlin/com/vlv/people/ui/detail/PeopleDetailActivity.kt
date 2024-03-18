package com.vlv.people.ui.detail

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
import com.google.android.material.tabs.TabLayoutMediator
import com.vlv.common.data.people.People
import com.vlv.common.ui.AppBarState
import com.vlv.common.ui.AppBarStateChangeListener
import com.vlv.common.route.FINISH_AFTER_TRANSITION_EXTRA
import com.vlv.common.ui.extension.loadUrl
import com.vlv.common.route.EXTRA_PEOPLE
import com.vlv.data.database.data.ImageType
import com.vlv.extensions.getAttrColor
import com.vlv.people.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

class PeopleDetailActivity : AppCompatActivity(R.layout.people_detail_activity) {

    private val viewModel: PeopleDetailViewModel by viewModel()

    private val people: People? by extraProvider(EXTRA_PEOPLE, null)
    private val finishAfterTransition: Boolean by extraProvider(FINISH_AFTER_TRANSITION_EXTRA, default = true)

    private val tabs: TabLayout by viewProvider(R.id.tabs)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val collapsing: CollapsingToolbarLayout by viewProvider(R.id.collapsing)
    private val layout: ViewPager2 by viewProvider(R.id.layout)
    private val appBar: AppBarLayout by viewProvider(R.id.app_bar_layout)
    private val avatar: AppCompatImageView by viewProvider(R.id.avatar)
    private val title: AppCompatTextView by viewProvider(R.id.people_title)
    private val job: AppCompatTextView by viewProvider(R.id.people_job)

    private val texts = listOf(
        R.string.people_tab_about,
        R.string.people_tab_movie_credit,
        R.string.people_tab_series_credit,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            if(finishAfterTransition) finishAfterTransition() else finish()
        }
        toolbar.navigationContentDescription = getString(com.vlv.ui.R.string.common_back_content_description)
        avatar.clipToOutline = true

        val people = people ?: return
        people.profilePath.loadUrl(avatar, ImageType.PROFILE)

        setupMenu(people)
        configTabs()
        configAppBar()
        configViewPager()
        showExpanded()
    }

    private fun setupMenu(people: People) {
        toolbar.inflateMenu(com.vlv.ui.R.menu.common_detail_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                com.vlv.ui.R.id.heart -> {
                    changeFavorite(people)
                    true
                }
                else -> false
            }
        }
        updatedMenu(people)
    }

    private fun changeFavorite(people: People) {
        viewModel.changeFavorite(people).observe(this) {
            data { isFavorite ->
                updatedMenuItem(isFavorite)
            }
        }
    }

    private fun updatedMenu(people: People) {
        viewModel.getFavorite(people.id).observe(this) {
            data {
                updatedMenuItem(isFavorite = it)
            }
        }
    }

    private fun updatedMenuItem(isFavorite : Boolean) {
        runCatching {
            val menuHeart = toolbar.menu?.getItem(0) ?: return@runCatching
            menuHeart.icon = ContextCompat.getDrawable(
                this@PeopleDetailActivity,
                if(isFavorite) R.drawable.ic_heart_filled
                else R.drawable.ic_heart_enable
            )
        }
    }

    private fun configViewPager() {
        val people = people ?: return
        val adapter = PeopleDetailAdapter(texts.size, people, this)
        layout.adapter = adapter
        TabLayoutMediator(tabs, layout) { tab, position ->
            tab.text = getString(texts[position])
        }.attach()
    }

    private fun configTabs() {
        texts.forEach { name ->
            tabs.addTab(
                tabs.newTab().apply {
                    text = getString(name)
                }
            )
        }
    }

    private fun configAppBar() {
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
                            R.color.color_imperiya_background
                        )
                    }
                    AppBarState.EXPANDED, AppBarState.IDLE, AppBarState.COLLAPSING -> {
                        ContextCompat.getColor(
                            this,
                            R.color.imperiya_color_transparent
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

    private fun showCollapsed(fullCollapsed: Boolean) {
        val people = people ?: return
        collapsing.isInvisible = fullCollapsed
        collapsing.title = people.name
        title.text = people.name
        job.text = people.department
    }

    private fun showExpanded() {
        val people = people ?: return
        collapsing.title = null
        title.text = people.name
        job.text = people.department
        title.isVisible = title.text.isNullOrBlank().not()
        job.isVisible = job.text.isNullOrBlank().not()
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finishAfterTransition()"))
    override fun onBackPressed() {
        if(finishAfterTransition) finishAfterTransition() else finish()
    }
    
}