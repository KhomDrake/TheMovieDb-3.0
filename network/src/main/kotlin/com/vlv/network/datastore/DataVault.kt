package com.vlv.network.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vlv.network.repository.SettingsOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

private const val DATA_STORE_KEY = "93481903dkhsj"

val dataVaultScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

object DataVault {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_KEY)
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

    fun language() = "${cachedDataString(SettingsOption.LANGUAGE.name)}-${cachedDataString(SettingsOption.REGION.name)}"

    suspend fun setValue(key: String, value: String) {
        cachedData[key] = value
        dataStore?.apply {
            val newKey = stringPreferencesKey(key)
            dataStore?.edit { settings ->
                settings[newKey] = value
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
            }
        }
    }

    fun setValue(key: String, value: Boolean, scope: CoroutineScope = dataVaultScope) {
        scope.launch {
            setValue(key, value)
        }
    }

    fun setValue(key: String, value: String, scope: CoroutineScope = dataVaultScope) {
        scope.launch {
            setValue(key, value)
        }
    }

    suspend fun getValue(key: String) : String? {
        return dataStore?.data?.map { settings: Preferences ->
            val newKey = stringPreferencesKey(key)
            settings[newKey]
        }?.first()
    }

    suspend fun getValueBoolean(key: String) : Boolean? {
        return dataStore?.data?.map { settings: Preferences ->
            val newKey = booleanPreferencesKey(key)
            settings[newKey]
        }?.first()
    }

}