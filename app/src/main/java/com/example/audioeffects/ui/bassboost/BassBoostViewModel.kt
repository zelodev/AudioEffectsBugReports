package com.example.audioeffects.ui.bassboost

import android.app.Application
import android.media.audiofx.BassBoost
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.BassBoostData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BassBoostViewModel(application: Application) : AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val bassBoostData = BassBoostData(application.dataStore)

    private var bassBoost: BassBoost? = null

    init {
        viewModelScope.launch {
            (application as AudioEffects).audioSessionId.collect { audioSession ->
                if (audioSession != null) {
                    /*
                     * BassBoost changed around Android 9. Since then bass boost became bass
                     * reduction. It sound just bad after this release.
                     * I can't image this being a feature.
                     */
                    bassBoost = BassBoost(0, audioSession).also {
                        it.enabled = bassBoostData.enable.first()
                        it.setStrength(bassBoostData.strength.first())
                    }
                } else {
                    bassBoost?.release()
                    bassBoost = null
                }
            }
        }
        viewModelScope.launch {
            bassBoostData.enable.collectLatest {
                bassBoost?.enabled = it
            }
        }
        viewModelScope.launch {
            bassBoostData.strength.collectLatest {
                bassBoost?.setStrength(it)
            }
        }
    }
}
