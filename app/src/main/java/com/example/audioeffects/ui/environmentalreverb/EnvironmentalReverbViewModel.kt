package com.example.audioeffects.ui.environmentalreverb

import android.app.Application
import android.media.audiofx.EnvironmentalReverb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.EnvironmentalReverbData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EnvironmentalReverbViewModel(application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val environmentalReverbData = EnvironmentalReverbData(application.dataStore)

    private var environmentalReverb: EnvironmentalReverb? = null

    init {
        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collect { audioSession ->
                if (audioSession!= null) {
                    environmentalReverb = EnvironmentalReverb(0, audioSession).also {
                        it.enabled = environmentalReverbData.enable.first()
                        it.decayHFRatio = environmentalReverbData.decayHfRatio.first()
                        it.decayTime = environmentalReverbData.decayTime.first()
                        it.density = environmentalReverbData.density.first()
                        it.diffusion = environmentalReverbData.diffusion.first()
                        it.reflectionsDelay = environmentalReverbData.reflectionsDelay.first()
                        it.reflectionsLevel = environmentalReverbData.reflectionsLevel.first()
                        it.reverbDelay = environmentalReverbData.reverbDelay.first()
                        it.reverbLevel = environmentalReverbData.reverbLevel.first()
                        it.roomHFLevel = environmentalReverbData.roomHfLevel.first()
                        it.roomLevel = environmentalReverbData.roomLevel.first()
                    }
                }
            }
        }
        viewModelScope.launch {
            environmentalReverbData.enable.collectLatest {
                environmentalReverb?.enabled = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.decayHfRatio.collectLatest {
                environmentalReverb?.decayHFRatio = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.decayTime.collectLatest {
                environmentalReverb?.decayTime = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.density.collectLatest {
                environmentalReverb?.density = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.diffusion.collectLatest {
                environmentalReverb?.density = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.reflectionsDelay.collectLatest {
                environmentalReverb?.reflectionsDelay = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.reflectionsLevel.collectLatest {
                environmentalReverb?.reverbLevel = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.reverbDelay.collectLatest {
                environmentalReverb?.reverbDelay = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.reverbLevel.collectLatest {
                environmentalReverb?.reverbLevel = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.roomHfLevel.collectLatest {
                environmentalReverb?.roomHFLevel = it
            }
        }
        viewModelScope.launch {
            environmentalReverbData.roomLevel.collectLatest {
                environmentalReverb?.roomLevel = it
            }
        }
    }
}
