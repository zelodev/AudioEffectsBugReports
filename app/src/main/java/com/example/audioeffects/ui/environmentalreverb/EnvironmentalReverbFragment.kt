package com.example.audioeffects.ui.environmentalreverb

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class EnvironmentalReverbFragment: PreferenceFragmentCompat() {

    private val viewModel by viewModels<EnvironmentalReverbViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.environmental_reverb, rootKey)
    }
}