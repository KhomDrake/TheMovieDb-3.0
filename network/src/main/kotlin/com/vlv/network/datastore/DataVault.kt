package com.vlv.network.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import java.lang.ref.WeakReference

private const val DATA_STORE_KEY = "93481903dkhsj"

object DataVault : KoinComponent {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_KEY)
    private var context: WeakReference<Context> = WeakReference(null)

    private val dataStore: DataStore<Preferences>?
        get() = context.get()?.dataStore

    private val cachedData: MutableMap<String, String> = mutableMapOf()

    fun init(ctx: Context) {
        context = WeakReference(ctx)
        context.get()?.dataStore
    }

    fun cachedData(key: String) = cachedData[key]

    suspend fun setValue(key: String, value: String) {
        dataStore?.apply {
            val newKey = stringPreferencesKey(key)
            dataStore?.edit { settings ->
                settings[newKey] = value
                cachedData[key]  = value
            }
        }
    }

    suspend fun getValue(key: String) : String? {
        return dataStore?.data?.map { settings: Preferences ->
            val newKey = stringPreferencesKey(key)
            settings[newKey]
        }?.first()
    }

}