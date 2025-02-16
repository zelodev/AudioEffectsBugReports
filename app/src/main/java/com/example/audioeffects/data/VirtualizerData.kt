package com.example.audioeffects.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class VirtualizerData(dataStore: DataStore<Preferences>) {

    val enable = dataStore.data.map {
        it[ENABLE] ?: false
    }.distinctUntilChanged()

    val strength = dataStore.data.map {
        it[STRENGTH]?.toShort() ?: 0
    }.distinctUntilChanged()

    private companion object {
        val ENABLE = booleanPreferencesKey("virtualizer_enable")
        val STRENGTH = intPreferencesKey("virtualizer_strength")
    }
}