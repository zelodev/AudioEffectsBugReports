package com.example.audioeffects.ui.dynamicsprocessing

import android.app.Application
import android.media.audiofx.DynamicsProcessing
import android.media.audiofx.DynamicsProcessing.EqBand
import android.media.audiofx.DynamicsProcessing.MbcBand
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioeffects.AudioEffects
import com.example.audioeffects.data.DynamicsProcessingData
import com.example.audioeffects.data.dataStore
import com.example.audioeffects.util.PreferenceDataStoreBridge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.log10
import kotlin.math.pow

class DynamicsProcessingViewModel(private val application: Application) :
    AndroidViewModel(application) {

    val dataStore = PreferenceDataStoreBridge(viewModelScope, application.dataStore)
    private val dynamicsProcessingData = DynamicsProcessingData(application.dataStore)

    private var dynamicsProcessing: DynamicsProcessing? = null

    private val mbcBandCount = MutableStateFlow(1)
    private val postEqBandCount = MutableStateFlow(32)

    private val config = combine(
        dynamicsProcessingData.preEqBandCount,
        mbcBandCount,
        postEqBandCount,
        dynamicsProcessingData.preferredTimeDuration,
    ) { preEqBandCount, mbcBandCount, postEqBandCount, preferredTimeframeDuration ->
        DynamicsProcessing.Config.Builder(
            DynamicsProcessing.VARIANT_FAVOR_FREQUENCY_RESOLUTION,
            2,
            true, preEqBandCount,
            true, mbcBandCount,
            true, postEqBandCount,
            true
        ).setPreferredFrameDuration(preferredTimeframeDuration)
    }

    private val preEq =
        dynamicsProcessingData.preEqEnable.map {
            DynamicsProcessing.Eq(
                true,
                it,
                dynamicsProcessingData.preEqBandCount.first(),
            ).apply {
                repeat(bandCount) { bandIndex ->
                    val frequency = calculateFrequency(bandIndex, bandCount)
                    val eqBand = EqBand(true, frequency.toFloat(), -5.0f)
                    setBand(bandIndex, eqBand)
                }
            }
        }

    private val mbc = combine(
        dynamicsProcessingData.mbcEnable,
        dynamicsProcessingData.mbcFrequency,
        dynamicsProcessingData.mbcGain,
        dynamicsProcessingData.mbcRatio,
        dynamicsProcessingData.mbcExpanderRatio,
    ) { enable, frequency, gain, mbcRatio, mbcExpanderRatio ->
        DynamicsProcessing.Mbc(
            true,
            enable,
            mbcBandCount.value,
        ).apply {
            val mbcBand = MbcBand(
                true,
                frequency, // cutoffFrequency
                3f, // attackTime
                80f, // releaseTime
                mbcRatio * 0.02f + 0.9f, // ratio
                -45f, // threshold
                1f, // kneeWidth
                -90f, // noiseGateThreshold
                mbcExpanderRatio * 0.02f + 0.9f, // expanderRatio
                0f, // preGain
                gain // postGain
            )
            setBand(0, mbcBand)
        }
    }

    private fun calculateFrequency(n: Int, bandCount: Int): Double {
        // Equal spread frequencies over band count for simplicity
        return 20.0 * 10.0.pow(n * (log10(1000.0) / (bandCount - 1)))
    }

    private suspend fun recreateDynamicsProcessing(
        audioSessionId: Int,
        config: DynamicsProcessing.Config.Builder
    ) {
        dynamicsProcessing?.release()
        dynamicsProcessing = null
        dynamicsProcessing = DynamicsProcessing(0, audioSessionId, config.build()).also {
            it.enabled = dynamicsProcessingData.enable.first()
            it.setPreEqAllChannelsTo(preEq.first())
            it.setMbcAllChannelsTo(mbc.first())
        }
    }

    init {
        viewModelScope.launch {
            combine(
                config,
                (application as AudioEffects).audioSessionId,
            ) { config, sessionId ->
                Pair(config, sessionId)
            }
                .collect { (config, audioSessionId) ->
                    if (audioSessionId != null) {
                        recreateDynamicsProcessing(audioSessionId, config)
                    } else {
                        dynamicsProcessing?.release()
                        dynamicsProcessing = null
                    }
                }
        }
        viewModelScope.launch {
            dynamicsProcessingData.enable.collectLatest {
                dynamicsProcessing?.enabled = it
            }
        }
        viewModelScope.launch {
            preEq.collectLatest {
                dynamicsProcessing?.setPreEqAllChannelsTo(it)
            }
        }
        viewModelScope.launch {
            mbc.collectLatest {
                dynamicsProcessing?.setMbcAllChannelsTo(it)
            }
        }
    }
}