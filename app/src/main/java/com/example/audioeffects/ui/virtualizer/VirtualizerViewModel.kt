package com.example.audioeffects.ui.virtualizer

import android.app.Application
import android.media.audiofx.Virtualizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.VirtualizerData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class VirtualizerViewModel(private val application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val virtualizerData = VirtualizerData(application.dataStore)

    init {
        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collect { audioSession ->
                if (audioSession != null) {
                    virtualizer = Virtualizer(0, audioSession).also {
                        it.setStrength(virtualizerData.strength.first())
                        it.enabled = virtualizerData.enable.first()
                    }
                } else {
                    virtualizer?.release()
                    virtualizer = null
                }
            }
        }
        viewModelScope.launch {
            virtualizerData.enable.collectLatest {
                virtualizer?.enabled = it

                /*
                 * BUG
                 * Workaround to get virtualizer working on Android 13 or 14. When enabling
                 * Virtualizer, forceVirtualizationMode needs to be set with a delay.
                 */
                delay(50)
                virtualizer?.forceVirtualizationMode(Virtualizer.VIRTUALIZATION_MODE_BINAURAL)
            }
        }
        viewModelScope.launch {
            virtualizerData.strength.collectLatest { virtualizer?.setStrength(it) }
        }
    }

    private var virtualizer: Virtualizer? = null
}