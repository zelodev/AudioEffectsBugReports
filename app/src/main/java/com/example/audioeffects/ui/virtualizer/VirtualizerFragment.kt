package com.example.audioeffects.ui.virtualizer

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class VirtualizerFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<VirtualizerViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.virtualizer, rootKey)
    }
}