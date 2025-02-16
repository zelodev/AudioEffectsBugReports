package com.example.audioeffects.ui.equalizer

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class EqualizerFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<EqualizerViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.equalizer, rootKey)

        preferenceScreen.getPreference(1).setOnPreferenceClickListener {
            it.summary = viewModel.togglePreset()
            true
        }
    }
}