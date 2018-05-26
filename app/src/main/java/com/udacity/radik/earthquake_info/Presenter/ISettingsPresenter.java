package com.udacity.radik.earthquake_info.Presenter;

import android.content.SharedPreferences;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.udacity.radik.earthquake_info.View.SettingsActivity.ISettingsFragment;

public interface ISettingsPresenter {

    void onAttachFragment(ISettingsFragment fragment);
    void onDetachFragment();
    void setAllPreferencesSummary(SharedPreferences sharedPreferences, PreferenceScreen preferenceScreen);
    void setPreferenceSummary(Preference p, SharedPreferences sharedPreferences);
    boolean isCorrectInput(Preference preference, Object newValue);
    void loadCountriesInfo();
}
