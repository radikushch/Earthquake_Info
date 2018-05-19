package com.udacity.radik.earthquake_info.View.SettingsActivity;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.udacity.radik.earthquake_info.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);
    }
}
