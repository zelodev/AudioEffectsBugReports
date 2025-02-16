package com.example.audioeffects.ui.bassboost

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class BassBoostFragment: PreferenceFragmentCompat() {

    private val viewModel by viewModels<BassBoostViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.bassboost, rootKey)
    }
}