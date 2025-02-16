package com.example.audioeffects.ui.presetreverb

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class PresetReverbFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<PresetReverbViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.preset_reverb, rootKey)
    }
}