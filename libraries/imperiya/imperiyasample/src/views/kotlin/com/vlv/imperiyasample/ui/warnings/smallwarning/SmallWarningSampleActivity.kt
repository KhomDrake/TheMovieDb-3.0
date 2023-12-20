package com.vlv.imperiyasample.ui.warnings.smallwarning

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.imperiyasample.R

class SmallWarningSampleActivity : AppCompatActivity(R.layout.activity_small_warning_sample) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val smallWarning: SmallWarningView by viewProvider(R.id.small_warning_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        smallWarning.setOnClickLink {
            Toast.makeText(this, "Try again clicked", Toast.LENGTH_SHORT).show()
        }
    }

}