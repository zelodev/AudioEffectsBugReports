package com.example.audioeffects.ui.loudnessenhancer

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class LoudnessEnhancerFragment: PreferenceFragmentCompat() {

    private val viewModel by viewModels<LoudnessEnhancerViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.loudness_enhancer, rootKey)
    }


}