package com.vlv.imperiyasample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiyasample.ui.error.WarningSampleActivity
import com.vlv.imperiyasample.ui.search.SearchSampleActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val components: RecyclerView by viewProvider(R.id.components)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        components.layoutManager = LinearLayoutManager(this)
        components.adapter = ComponentsAdapter(listOf(
            Component(
                "Search", SearchSampleActivity::class
            ),
            Component(
                "Warning", WarningSampleActivity::class
            )
        ))

    }

}