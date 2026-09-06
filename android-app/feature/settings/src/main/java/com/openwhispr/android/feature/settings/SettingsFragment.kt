package com.openwhispr.android.feature.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_root, rootKey)
    }
}
