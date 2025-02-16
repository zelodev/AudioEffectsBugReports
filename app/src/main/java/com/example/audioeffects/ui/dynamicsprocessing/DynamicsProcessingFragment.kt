package com.example.audioeffects.ui.dynamicsprocessing

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class DynamicsProcessingFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<DynamicsProcessingViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.dynamics_processing, rootKey)
    }
}