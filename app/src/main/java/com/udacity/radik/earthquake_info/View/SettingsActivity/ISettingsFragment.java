package com.udacity.radik.earthquake_info.View.SettingsActivity;

import android.content.SharedPreferences;

import com.udacity.radik.earthquake_info.Model.Data.Countries.GeoNames;

import java.util.List;

public interface ISettingsFragment {

    void showNumberInputError();
    void showDateInputError();
    String getMagnitudeKey();
    String getFirstDateKey();
    String getLastDateKey();

    void setListPreference(String[] listOfCountries);
}
