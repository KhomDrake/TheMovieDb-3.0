package com.vlv.imperiyasample.ui.warnings.warning.examples

import android.os.Bundle
import android.widget.Toast

class WarningErrorSampleActivity : DefaultWarningSampleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        warningView.apply {
            setTitle("Error Title")
            setBody("Error Body")
            setButtonText("Try again button text")
            setStatusIcon(com.vlv.imperiya.core.R.drawable.ic_close_error)
            setOnTryAgain {
                Toast.makeText(this@WarningErrorSampleActivity, "Clicked", Toast.LENGTH_SHORT).show()
            }
            setCloseIcon {
                finish()
            }
        }
    }

}