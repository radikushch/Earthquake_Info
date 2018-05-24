package com.udacity.radik.earthquake_info.Presenter;

import android.content.SharedPreferences;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import com.udacity.radik.earthquake_info.View.SettingsActivity.ISettingsFragment;

public class SettingsPresenter implements ISettingsPresenter {

    private ISettingsFragment fragment;

    @Override
    public void onAttachFragment(ISettingsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onDetachFragment() {
        this.fragment = null;
    }

    @Override
    public void setAllPreferencesSummary(SharedPreferences sharedPreferences, PreferenceScreen preferenceScreen) {
        int preferencesCount = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < preferencesCount; i++) {
            Preference p = preferenceScreen.getPreference(i);
            p.setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) fragment);
            if(!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setSummary(p, value);
            }
        }
    }

    private void setSummary(Preference p, String value) {
        if(p instanceof EditTextPreference) {
            p.setSummary(value);
        }
    }

    @Override
    public void setPreferenceSummary(Preference preference, SharedPreferences sharedPreferences) {
        if(preference != null) {
            if(!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setSummary(preference, value);
            }
        }
    }

    @Override
    public boolean isCorrectInput(Preference preference, Object newValue) {
        String magnitudeKey = fragment.getMagnitudeKey();
        if(preference.getKey().equals(magnitudeKey)) {
            String stringMagnitude = (String) newValue;
            return isMagnitudeCorrect(stringMagnitude);
        }
        String firstDateKey = fragment.getFirstDateKey();
        String lastDateKey = fragment.getLastDateKey();
        if(preference.getKey().equals(firstDateKey) || preference.getKey().equals(lastDateKey)) {
            String stringDate = (String) newValue;
            return isDateCorrect(stringDate);
        }
        return true;
    }

    private boolean isMagnitudeCorrect(String stringMagnitude) {
        try {
            int power = Integer.parseInt(stringMagnitude);
            if(power > 10 || power < 0) {
                fragment.showNumberInputError();
                return false;
            }
        } catch(NumberFormatException e) {
            fragment.showNumberInputError();
            return false;
        }
        return true;
    }

    private boolean isDateCorrect(String stringDate) {
        char delimiter1, delimiter2;
        delimiter1 = stringDate.charAt(4);
        delimiter2 = stringDate.charAt(7);
        if(delimiter1 != ':' || delimiter2 != ':') {
            fragment.showDateInputError();
            return false;
        }
        return true;
    }
}
