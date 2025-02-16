package com.example.audioeffects.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class HapticGeneratorData(dataStore: DataStore<Preferences>) {

    val enable = dataStore.data.map {
        it[ENABLE] ?: false
    }.distinctUntilChanged()

    private companion object {
        val ENABLE = booleanPreferencesKey("haptic_generator_enable")
    }
}