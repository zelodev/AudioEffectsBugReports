package com.example.audioeffects.ui.hapticgenerator

import android.app.Application
import android.media.audiofx.HapticGenerator
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.HapticGeneratorData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HapticGeneratorViewModel(application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val hapticGeneratorData = HapticGeneratorData(application.dataStore)

    private var hapticGenerator: HapticGenerator? = null

    init {
        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collectLatest { audioSession ->
                if (audioSession != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && HapticGenerator.isAvailable()) {
                        hapticGenerator = HapticGenerator.create(audioSession).also {
                            it.enabled = hapticGeneratorData.enable.first()
                        }
                    }
                } else {
                    hapticGenerator?.release()
                    hapticGenerator = null
                }
            }
        }
        viewModelScope.launch {
            hapticGeneratorData.enable.collect {
                hapticGenerator?.enabled = it
            }
        }
    }
}
