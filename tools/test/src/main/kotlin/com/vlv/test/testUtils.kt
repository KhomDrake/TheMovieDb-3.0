package com.vlv.test

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.google.common.reflect.TypeToken
import com.squareup.moshi.Moshi
import io.mockk.every
import io.mockk.mockkStatic
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun mockIntent(action: String, targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intending(
        IntentMatchers.hasAction(
            "${context.packageName}.$action"
        )
    ).respondWith(Instrumentation.ActivityResult(0, null))
}

fun mockIntent(action: String, targetContext: Boolean = false, vararg matchers: Matcher<Intent>) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intending(
        Matchers.allOf(
            matchers.toMutableList().apply {
                add(IntentMatchers.hasAction(
                    "${context.packageName}.$action"
                ))
            }
        )
    ).respondWith(Instrumentation.ActivityResult(0, null))
}

fun checkIntent(action: String, targetContext: Boolean = false, vararg matchers: Matcher<Intent>) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intended(
        Matchers.allOf(
            matchers.toMutableList().apply {
                add(IntentMatchers.hasAction(
                    "${context.packageName}.$action"
                ))
            }
        )
    )
}

fun checkIntent(action: String, targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intended(
        IntentMatchers.hasAction(
            "${context.packageName}.$action"
        )
    )
}

fun mockNavigationActivity() {
    mockkStatic("androidx.navigation.Navigation")
}

fun mockNavigationFragment() {
    mockkStatic("androidx.navigation.fragment.NavHostFragment")
}

inline fun <reified T> loadObjectFromJson(context: Context, file: String, moshi: Moshi) : T? {
    val json = context.resources.assets.open(file)
        .bufferedReader()
        .readText()

    return moshi.adapter(T::class.java).fromJson(json)
}

inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

//inline fun <reified T> loadListFromJson(context: Context, file: String, moshi: Moshi, type: Type = genericType<List<T>>()) : List<T>? {
//    val json = context.resources.assets.open(file)
//        .bufferedReader()
//        .readText()
//    return moshi.adapter(List<T>::class).fromJson(json)
//}

inline fun <reified T>MutableResponseLiveData<T>.postTestData(json: String, moshi: Moshi) {
    val data = loadObjectFromJson<T>(ApplicationProvider.getApplicationContext(), json, moshi) ?: return
    postData(data)
}

//inline fun <reified T>MutableResponseLiveData<List<T>>.postTestDataList(json: String, moshi: Moshi) {
//    val data = loadListFromJson<T>(ApplicationProvider.getApplicationContext(), json, moshi) ?: return
//    postData(data)
//}


inline fun <reified T>mockResponseRepository(
    crossinline request: () -> ResponseLiveData<T>
) : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke() } returns liveData
    return liveData
}

inline fun <reified T, reified First: Any>mockResponseRepository(
    crossinline request: (First) -> ResponseLiveData<T>
) : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke(any()) } returns liveData
    return liveData
}

inline fun <reified T, reified First: Any, reified Second: Any>mockResponseRepository(
    crossinline request: (First, Second) -> ResponseLiveData<T>
) : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke(any(), any()) } returns liveData
    return liveData
}

inline fun <reified T, reified First: Any, reified Second: Any, reified Third: Any>mockResponseRepository(
    crossinline request: (First, Second, Third) -> ResponseLiveData<T>
)  : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke(any(), any(), any()) } returns liveData
    return liveData
}
