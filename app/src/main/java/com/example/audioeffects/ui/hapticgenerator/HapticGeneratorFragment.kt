package com.example.audioeffects.ui.hapticgenerator

import android.media.audiofx.HapticGenerator
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import com.example.audioeffects.R

class HapticGeneratorFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModels<HapticGeneratorViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = viewModel.dataStore
        setPreferencesFromResource(R.xml.haptic_generator, rootKey)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            Toast.makeText(requireContext(), "Not available in this Android version", Toast.LENGTH_SHORT).show()
        } else if (!HapticGenerator.isAvailable()) {
            Toast.makeText(requireContext(), "Haptic generator not available in this ROM", Toast.LENGTH_SHORT).show()
        }
    }
}