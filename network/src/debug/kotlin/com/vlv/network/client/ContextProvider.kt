package com.vlv.network.client

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

object ContextProvider : ActivityLifecycleCallbacks {
    
    var context: WeakReference<Context?> = WeakReference(null)
    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        context = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        
    }

    override fun onActivityResumed(activity: Activity) {
        
    }

    override fun onActivityPaused(activity: Activity) {
        
    }

    override fun onActivityStopped(activity: Activity) {
        
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
        
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}