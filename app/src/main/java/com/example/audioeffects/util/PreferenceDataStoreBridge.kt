package com.example.audioeffects.util


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PreferenceDataStoreBridge(
    lifecycleScope: CoroutineScope,
    private val dataStore: DataStore<Preferences>,
) : PreferenceDataStore(), CoroutineScope by lifecycleScope {

    override fun putString(key: String, value: String?) {
        putPreference(stringPreferencesKey(key), value)
    }

    override fun putStringSet(key: String, values: MutableSet<String>?) {
        putPreference(stringSetPreferencesKey(key), values)
    }

    override fun putInt(key: String, value: Int) {
        putPreference(intPreferencesKey(key), value)
    }

    override fun putLong(key: String, value: Long) {
        putPreference(longPreferencesKey(key), value)
    }

    override fun putFloat(key: String, value: Float) {
        putPreference(floatPreferencesKey(key), value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        putPreference(booleanPreferencesKey(key), value)
    }

    override fun getString(key: String, defValue: String?) = runBlocking {
        dataStore.data.map { it[stringPreferencesKey(key)] ?: defValue }.first()
    }

    override fun getStringSet(key: String, defValues: MutableSet<String>?) = runBlocking {
        dataStore.data.map { it[stringSetPreferencesKey(key)] ?: defValues }.first()
    }

    override fun getInt(key: String, defValue: Int) = runBlocking {
        dataStore.data.map { it[intPreferencesKey(key)] ?: defValue }.first()
    }

    override fun getLong(key: String, defValue: Long) = runBlocking {
        dataStore.data.map { it[longPreferencesKey(key)] ?: defValue }.first()
    }

    override fun getFloat(key: String, defValue: Float) = runBlocking {
        dataStore.data.map { it[floatPreferencesKey(key)] ?: defValue }.first()
    }

    override fun getBoolean(key: String, defValue: Boolean) = runBlocking {
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: defValue }.first()
    }

    private fun <T> putPreference(key: Preferences.Key<T>, value: T?) {
        launch {
            dataStore.edit {
                if (value == null) {
                    it.remove(key)
                } else {
                    it[key] = value
                }
            }
        }
    }
}