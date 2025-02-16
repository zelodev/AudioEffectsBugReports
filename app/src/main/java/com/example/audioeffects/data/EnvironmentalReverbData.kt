package com.example.audioeffects.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class EnvironmentalReverbData(dataStore: DataStore<Preferences>) {

    val enable = dataStore.data.map {
        it[ENABLE] ?: false
    }.distinctUntilChanged()

    val decayHfRatio = dataStore.data.map {
        it[DECAY_HF_RATIO]?.toShort() ?: 0
    }.distinctUntilChanged()

    val decayTime = dataStore.data.map {
        it[DECAY_TIME] ?: 0
    }.distinctUntilChanged()

    val density = dataStore.data.map {
        it[DENSITY]?.toShort() ?: 0
    }.distinctUntilChanged()

    val diffusion = dataStore.data.map {
        it[DIFFUSION]?.toShort() ?: 0
    }.distinctUntilChanged()

    val reflectionsDelay = dataStore.data.map {
        it[REFLECTIONS_DELAY] ?: 0
    }.distinctUntilChanged()

    val reflectionsLevel = dataStore.data.map {
        it[REFLECTIONS_LEVEL]?.toShort() ?: 0
    }.distinctUntilChanged()

    val reverbDelay = dataStore.data.map {
        it[REVERB_DELAY] ?: 0
    }.distinctUntilChanged()

    val reverbLevel = dataStore.data.map {
        it[REVERB_LEVEL]?.toShort() ?: 0
    }.distinctUntilChanged()

    val roomHfLevel = dataStore.data.map {
        it[ROOM_HF_LEVEL]?.toShort() ?: 0
    }.distinctUntilChanged()

    val roomLevel = dataStore.data.map {
        it[ROOM_LEVEL]?.toShort() ?: 0
    }.distinctUntilChanged()

    private companion object {
        val ENABLE = booleanPreferencesKey("environmental_reverb_enable")
        val DECAY_HF_RATIO = intPreferencesKey("decay_hf_ratio")
        val DECAY_TIME = intPreferencesKey("decay_time")
        val DENSITY = intPreferencesKey("density")
        val DIFFUSION = intPreferencesKey("diffusion")
        val REFLECTIONS_DELAY = intPreferencesKey("reflections_delay")
        val REFLECTIONS_LEVEL = intPreferencesKey("reflections_level")
        val REVERB_DELAY = intPreferencesKey("reverb_delay")
        val REVERB_LEVEL = intPreferencesKey("reverb_level")
        val ROOM_HF_LEVEL = intPreferencesKey("room_hf_level")
        val ROOM_LEVEL = intPreferencesKey("room_level")
    }
}