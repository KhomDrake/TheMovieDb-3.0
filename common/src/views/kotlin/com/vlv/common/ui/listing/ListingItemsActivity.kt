package com.vlv.common.ui.listing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.R

abstract class ListingItemsActivity : AppCompatActivity(R.layout.common_listing_activity) {

    protected val items: RecyclerView by viewProvider(R.id.items)
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    abstract val title: Int
    abstract val adapter: Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.title = getString(title)

        configRecyclerView()
    }

    private fun configRecyclerView() {
        items.layoutManager = createLayoutManager()
        items.adapter = adapter
    }

    open fun createLayoutManager() : LayoutManager = GridLayoutManager(this, 2)

}