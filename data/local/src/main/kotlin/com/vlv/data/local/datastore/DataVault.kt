package com.vlv.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import java.lang.ref.WeakReference

private const val DATA_STORE_KEY = "93481903dkhsj"

object DataVault : KoinComponent {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_KEY
    )
    private var context: WeakReference<Context> = WeakReference(null)

    private val dataStore: DataStore<Preferences>?
        get() = context.get()?.dataStore

    private val cachedData: MutableMap<String, Any> = mutableMapOf()

    fun init(ctx: Context) {
        context = WeakReference(ctx)
        context.get()?.dataStore
    }

    fun cachedDataBoolean(key: String) = (cachedData[key] as? Boolean) ?: false

    fun cachedDataString(key: String) = cachedData[key] as String

    suspend fun setValue(key: String, value: String) {
        cachedData[key] = value
        dataStore?.apply {
            val newKey = stringPreferencesKey(key)
            dataStore?.edit { settings ->
                settings[newKey] = value
                cachedData[key] = value
            }
        }
    }

    suspend fun loadCache() {
        dataStore?.data?.map { preferences ->
            preferences.asMap()
        }?.first()?.forEach { (key, any) ->
            cachedData[key.name] = any
        }
        cachedData
    }

    suspend fun containsKey(key: String) = dataStore?.data?.map { settings ->
        settings.contains(stringPreferencesKey(key))
    }?.first() ?: false

    suspend fun setValue(key: String, value: Boolean) {
        cachedData[key] = value
        dataStore?.apply {
            val newKey = booleanPreferencesKey(key)
            dataStore?.edit { settings ->
                settings[newKey] = value
                cachedData[key] = value
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