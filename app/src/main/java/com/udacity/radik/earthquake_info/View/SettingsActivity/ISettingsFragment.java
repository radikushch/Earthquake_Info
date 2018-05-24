package com.udacity.radik.earthquake_info.View.SettingsActivity;

import android.content.SharedPreferences;

public interface ISettingsFragment {

    void showNumberInputError();
    void showDateInputError();
    String getMagnitudeKey();
    String getFirstDateKey();
    String getLastDateKey();
}
