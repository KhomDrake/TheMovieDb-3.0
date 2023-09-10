package com.vlv.imperiyasample.ui.error

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.warning.WarningView
import com.vlv.imperiyasample.R

class BigWarningSampleActivity : AppCompatActivity(R.layout.activity_big_warning_sample) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val warning: WarningView by viewProvider(R.id.warning_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        warning.setOnTryAgain {
            Toast.makeText(this, "Try again clicked", Toast.LENGTH_SHORT).show()
        }
    }

}