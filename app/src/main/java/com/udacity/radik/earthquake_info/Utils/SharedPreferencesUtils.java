package com.udacity.radik.earthquake_info.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.udacity.radik.earthquake_info.EarthQuakeApplication;
import com.udacity.radik.earthquake_info.Model.Data.Countries.GeoNames;
import com.udacity.radik.earthquake_info.R;
import com.udacity.radik.earthquake_info.View.MainActivity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtils {

    private final static String FORMAT_KEY = "format";
    private final static String FORMAT_TYPE = "geojson";
    private final static String START_TIME_KEY = "starttime";
    private final static String END_TIME_KEY = "endtime";
    private final static String MIN_MAG_KEY = "minmagnitude";
    private final static String MIN_LATITUDE_KEY = "minlatitude";
    private final static String MIN_LONGITUDE_KEY = "minlongitude";
    private final static String MAX_LATITUDE_KEY = "maxlatitude";
    private final static String MAX_LONGITUDE_KEY = "maxlongitude";

    public interface SharedPreferencesCallback {
        void loadData(Map<String, String> parameters);
    }

    private static SharedPreferencesCallback callback;

    public static void registerCallBack(SharedPreferencesCallback callback) {
        SharedPreferencesUtils.callback = callback;
    }

    public static void getQueryParameters(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String magnitude = sharedPreferences.getString(context.getString(R.string.magnitude_key),
                context.getString(R.string.magnitude_default_value));
        String firstDate = sharedPreferences.getString(context.getString(R.string.time_start_key),
                        context.getString(R.string.time_start_default_value));
        String lastDate = sharedPreferences.getString(context.getString(R.string.time_end_key),
                context.getString(R.string.time_end_default_value));

        firstDate = firstDate.replaceAll(":", "-");
        lastDate = lastDate.replaceAll(":", "-");

        Map<String, String> parameters = new HashMap<>(4);
        parameters.put(FORMAT_KEY, FORMAT_TYPE);
        parameters.put(START_TIME_KEY, firstDate);
        parameters.put(END_TIME_KEY, lastDate);
        parameters.put(MIN_MAG_KEY, magnitude);
        String countryName = sharedPreferences
                .getString(context.getString(R.string.location_key), "All");
        if(!countryName.equals("All")) {
            parameters.put("country_name", countryName);
            new ParametersAsyncTask().execute(parameters);
        }else {
            callback.loadData(parameters);
        }
    }

    private static class ParametersAsyncTask extends AsyncTask<Map<String, String>, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Map<String, String>... maps) {
            Map<String, String> parameters = maps[0];
            String countryName = parameters.get("country_name");
            parameters.remove("country_name");
            GeoNames geoNames = EarthQuakeApplication
                    .getInstance()
                    .getDatabase()
                    .geoNamesDAO()
                    .getByName(countryName);
            parameters.put(MIN_LATITUDE_KEY, String.valueOf(geoNames.getSouth()));
            parameters.put(MIN_LONGITUDE_KEY, String.valueOf(geoNames.getWest()));
            parameters.put(MAX_LATITUDE_KEY, String.valueOf(geoNames.getNorth()));
            parameters.put(MAX_LONGITUDE_KEY, String.valueOf(geoNames.getEast()));
            return parameters;
        }

        @Override
        protected void onPostExecute(Map<String, String> parameters) {
            SharedPreferencesUtils.callback.loadData(parameters);
        }
    }
}
