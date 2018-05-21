package com.udacity.radik.earthquake_info.View.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.udacity.radik.earthquake_info.Presenter.ISettingsPresenter;
import com.udacity.radik.earthquake_info.Presenter.SettingsPresenter;
import com.udacity.radik.earthquake_info.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener,
        ISettingsFragment {

    private ISettingsPresenter settingsPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        settingsPresenter = new SettingsPresenter();
        settingsPresenter.onAttachFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        settingsPresenter.onDetachFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        settingsPresenter.setAllPreferencesSummary(sharedPreferences, preferenceScreen);

        Preference preference = findPreference(getString(R.string.magnitude_key));
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        settingsPresenter.setPreferenceSummary(preference, sharedPreferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return settingsPresenter.isCorrectInput(preference, newValue);
    }

    @Override
    public void showError() {
        Toast error = Toast.makeText(getContext(),
                "Select a number between 0 and 10",
                Toast.LENGTH_SHORT);
        error.show();
    }

    @Override
    public String getMagnitudeKey() {
        return getString(R.string.magnitude_key);
    }

    @Override
    public String getFirstDateKey() {
        return getString(R.string.time_start_key);
    }

    @Override
    public String getLastDateKey() {
        return getString(R.string.time_end_key);
    }
}
