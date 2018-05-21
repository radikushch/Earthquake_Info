package com.udacity.radik.earthquake_info.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

    public static Map<String, String> getQueryParameters(Context context) {
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
        return parameters;
    }
}
