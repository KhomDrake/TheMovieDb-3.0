package com.vlv.bondsmith.log

import android.util.Log
import timber.log.Timber

object LogHandler {

    fun logInfo(tag: String, text: String) {
        Timber.tag(tag).d(text)
        Log.i(tag, text)
    }



}