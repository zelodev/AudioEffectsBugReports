package com.example.audioeffects.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DynamicsProcessingData(dataStore: DataStore<Preferences>) {

    val enable = dataStore.data.map {
        it[ENABLE] ?: false
    }.distinctUntilChanged()

    val preEqBandCount = dataStore.data.map {
        it[PRE_EQ_BANDCOUNT] ?: 30
    }.distinctUntilChanged()

    val preferredTimeDuration = dataStore.data.map {
        it[PREFERRED_TIME_DURATION]?.toFloat() ?: 10f
    }.distinctUntilChanged()

    val preEqEnable = dataStore.data.map {
        it[PRE_EQ_ENABLE] ?: false
    }.distinctUntilChanged()

    val mbcEnable = dataStore.data.map {
        it[MBC_ENABLE] ?: false
    }.distinctUntilChanged()

    val mbcRatio =  dataStore.data.map {
        it[MBC_RATIO]?.toFloat() ?: 5f
    }.distinctUntilChanged()

    val mbcExpanderRatio = dataStore.data.map {
        it[MBC_EXPANDER_RATIO]?.toFloat() ?: 5f
    }.distinctUntilChanged()

    val mbcGain = dataStore.data.map {
        it[MBC_GAIN]?.toFloat() ?: 0f
    }.distinctUntilChanged()

    val mbcFrequency = dataStore.data.map {
        it[MBC_FREQUENCY]?.toFloat() ?: 200f
    }.distinctUntilChanged()

    private companion object {
        val ENABLE = booleanPreferencesKey("dynamics_processing_enable")
        val PRE_EQ_BANDCOUNT = intPreferencesKey("pre_eq_bandcount")
        val PREFERRED_TIME_DURATION = intPreferencesKey("preferred_time_duration")
        val PRE_EQ_ENABLE = booleanPreferencesKey("pre_eq_enable")
        val MBC_ENABLE = booleanPreferencesKey("mbc_enable")
        val MBC_GAIN = intPreferencesKey("mbc_gain")
        val MBC_RATIO = intPreferencesKey("mbc_ratio")
        val MBC_EXPANDER_RATIO = intPreferencesKey("mbc_expander_ratio")
        val MBC_FREQUENCY = intPreferencesKey("mbc_frequency")
    }
}
