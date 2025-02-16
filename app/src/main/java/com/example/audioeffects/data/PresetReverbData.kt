package com.example.audioeffects.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class PresetReverbData(dataStore: DataStore<Preferences>) {

    val enable = dataStore.data.map {
        it[ENABLE] ?: false
    }.distinctUntilChanged()

    val preset = dataStore.data.map {
        it[PRESET]?.toShort() ?: 0
    }.distinctUntilChanged()

    private companion object {
        val ENABLE = booleanPreferencesKey("preset_reverb_enable")
        val PRESET = intPreferencesKey("preset_reverb_preset")
    }
}