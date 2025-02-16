package com.example.audioeffects.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class EqualizerData(dataStore: DataStore<Preferences>) {

    val enable = dataStore.data.map {
        it[ENABLE] ?: false
    }.distinctUntilChanged()

    val band0 = dataStore.data.map {
        it[BAND_0]?.toShort() ?: 0
    }.distinctUntilChanged()

    val band1 = dataStore.data.map {
        it[BAND_1]?.toShort() ?: 0
    }.distinctUntilChanged()

    val band2 = dataStore.data.map {
        it[BAND_2]?.toShort() ?: 0
    }.distinctUntilChanged()

    val band3 = dataStore.data.map {
        it[BAND_3]?.toShort() ?: 0
    }.distinctUntilChanged()

    val band4 = dataStore.data.map {
        it[BAND_4]?.toShort() ?: 0
    }.distinctUntilChanged()

    private companion object {
        val ENABLE = booleanPreferencesKey("equalizer_enable")
        val BAND_0 = intPreferencesKey("equalizer_band_0")
        val BAND_1 = intPreferencesKey("equalizer_band_1")
        val BAND_2 = intPreferencesKey("equalizer_band_2")
        val BAND_3 = intPreferencesKey("equalizer_band_3")
        val BAND_4 = intPreferencesKey("equalizer_band_4")
    }
}
