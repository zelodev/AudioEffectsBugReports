package com.example.audioeffects.ui.loudnessenhancer

import android.app.Application
import android.media.audiofx.LoudnessEnhancer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.LoudnessEnhancerData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoudnessEnhancerViewModel(application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val loudnessEnhancerData = LoudnessEnhancerData(application.dataStore)

    private var loudnessEnhancer: LoudnessEnhancer? = null

    init {

        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collect { audioSession ->
                if (audioSession != null) {
                    loudnessEnhancer = LoudnessEnhancer(audioSession).also {
                        it.enabled = loudnessEnhancerData.enable.first()
                        it.setTargetGain(loudnessEnhancerData.targetGain.first())
                    }
                } else {
                    loudnessEnhancer?.release()
                    loudnessEnhancer = null
                }
            }
        }
        viewModelScope.launch {
            loudnessEnhancerData.enable.collectLatest {
                loudnessEnhancer?.enabled = it
            }
        }
        viewModelScope.launch {
            loudnessEnhancerData.targetGain.collectLatest {
                loudnessEnhancer?.setTargetGain(it)
            }
        }
    }
}
