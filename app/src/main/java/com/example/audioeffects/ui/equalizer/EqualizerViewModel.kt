package com.example.audioeffects.ui.equalizer

import android.app.Application
import android.media.audiofx.Equalizer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.EqualizerData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EqualizerViewModel(application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val equalizerData = EqualizerData(application.dataStore)

    private var equalizer: Equalizer? = null

    init {
        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collectLatest { sessionId ->
                if (sessionId != null) {
                    equalizer = Equalizer(0, sessionId).also {
                        it.enabled = equalizerData.enable.first()

                        it.setBandLevel(0, equalizerData.band0.first())
                        it.setBandLevel(1, equalizerData.band1.first())
                        it.setBandLevel(2, equalizerData.band2.first())
                        it.setBandLevel(3, equalizerData.band3.first())
                        it.setBandLevel(4, equalizerData.band4.first())

                        it.setControlStatusListener { effect, controlGranted ->
                            /*
                             * BUG
                             * Notice how this listener never gets called. This is
                             * valid for every AudioEffect class.
                             */
                            Log.i("Equalizer", "Does ControlStatusListener ever get called?")
                        }
                        it.setParameterListener { effect, status, param1, param2, value ->
                            /*
                             * BUG
                             * Notice how this listener never gets called. This is
                             * valid for every AudioEffect class.
                             */
                            Log.i("Equalizer", "Does ParameterListener ever get called?")
                        }
                        it.setEnableStatusListener { effect, enabled ->
                            /*
                             * BUG
                             * Notice how this listener never gets called. This is
                             * valid for every AudioEffect class.
                             */
                            Log.i("Equalizer", "Does EnableStatusListener ever get called?")
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            equalizerData.enable.collectLatest {
                equalizer?.enabled = it
            }
        }
        viewModelScope.launch {
            equalizerData.band0.collectLatest {
                equalizer?.setBandLevel(0, it)
            }
        }
        viewModelScope.launch {
            equalizerData.band1.collectLatest {
                equalizer?.setBandLevel(1, it)
            }
        }
        viewModelScope.launch {
            equalizerData.band2.collectLatest {
                equalizer?.setBandLevel(2, it)
            }
        }
        viewModelScope.launch {
            equalizerData.band3.collectLatest {
                equalizer?.setBandLevel(3, it)
            }
        }
        viewModelScope.launch {
            equalizerData.band4.collectLatest {
                equalizer?.setBandLevel(4, it)
            }
        }
    }

    fun togglePreset(): String {
        return equalizer?.let {
            if (it.currentPreset == it.numberOfPresets) {
                it.usePreset(0.toShort())
                it.getPresetName(0.toShort())
            } else {
                it.usePreset((it.currentPreset + 1).toShort())
                it.getPresetName((it.currentPreset + 1).toShort())
            }
        } ?: ""
    }
}
