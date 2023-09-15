package com.vlv.imperiyasample.ui.warnings.warning

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiyasample.R
import com.vlv.imperiyasample.ui.warnings.warning.examples.WarningDefaultSampleActivity
import com.vlv.imperiyasample.ui.warnings.warning.examples.WarningErrorSampleActivity
import com.vlv.imperiyasample.ui.warnings.warning.examples.WarningSuccessSampleActivity
import com.vlv.imperiyasample.ui.warnings.warning.examples.WarningWithoutIconSampleActivity

class BigWarningSampleActivity : AppCompatActivity(R.layout.activity_big_warning_sample) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val warningErrorSample: AppCompatButton by viewProvider(R.id.warning_error_sample)
    private val warningSuccessSample: AppCompatButton by viewProvider(R.id.warning_success_sample)
    private val warningDefaultSample: AppCompatButton by viewProvider(R.id.warning_default_sample)
    private val warningWithoutIconSample: AppCompatButton by viewProvider(R.id.warning_without_icon_sample)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        warningErrorSample.setOnClickListener {
            startActivity(
                Intent(this, WarningErrorSampleActivity::class.java)
            )
        }

        warningSuccessSample.setOnClickListener {
            startActivity(
                Intent(this, WarningSuccessSampleActivity::class.java)
            )
        }

        warningDefaultSample.setOnClickListener {
            startActivity(
                Intent(this, WarningDefaultSampleActivity::class.java)
            )
        }

        warningWithoutIconSample.setOnClickListener {
            startActivity(
                Intent(this, WarningWithoutIconSampleActivity::class.java)
            )
        }

    }

}