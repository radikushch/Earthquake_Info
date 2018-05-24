package com.udacity.radik.earthquake_info.View.SettingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.widget.Toast;

import com.udacity.radik.earthquake_info.Presenter.ISettingsPresenter;
import com.udacity.radik.earthquake_info.Presenter.SettingsPresenter;
import com.udacity.radik.earthquake_info.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        Preference.OnPreferenceChangeListener, ISettingsFragment {


    private ISettingsPresenter settingsPresenter;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_screen);

        settingsPresenter = new SettingsPresenter();
        settingsPresenter.onAttachFragment(this);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();

        settingsPresenter.setAllPreferencesSummary(sharedPreferences, preferenceScreen);
    }


    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        settingsPresenter.onDetachFragment();
    }

    @Override
    public void showNumberInputError() {
        Toast.makeText(getActivity(), "Input number between 0 and 10", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDateInputError() {
        Toast.makeText(getActivity(), "Input date in such format: YYYY:MM:DD", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        settingsPresenter.setPreferenceSummary(preference, sharedPreferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return settingsPresenter.isCorrectInput(preference, newValue);
    }
}
