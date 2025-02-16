package com.example.audioeffects.ui.presetreverb

import android.app.Application
import android.media.audiofx.PresetReverb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.PresetReverbData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PresetReverbViewModel(private val application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val presetReverbData = PresetReverbData(application.dataStore)

    init {
        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collect { audioSession ->
                if (audioSession != null) {
                    presetReverb = PresetReverb(0, audioSession).also {
                        it.enabled = presetReverbData.enable.first()
                        it.preset = presetReverbData.preset.first()
                    }
                } else {
                    presetReverb?.release()
                    presetReverb = null
                }
            }
        }
        viewModelScope.launch {
            presetReverbData.enable.collectLatest { presetReverb?.enabled = it }
        }
        viewModelScope.launch {
            presetReverbData.preset.collectLatest { presetReverb?.preset = it }
        }
    }

    private var presetReverb: PresetReverb? = null
}